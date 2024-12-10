package com.bit.microservices.service_approval.services.impl;

import com.bit.microservices.service_approval.entity.MsEventApproval;
import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.enums.responsecode.FunctionServiceCodeEnum;
import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import com.bit.microservices.service_approval.enums.responsecode.ServiceCodeEnum;
import com.bit.microservices.service_approval.exceptions.*;
import com.bit.microservices.service_approval.exceptions.views.BadRequestViewException;
import com.bit.microservices.service_approval.exceptions.views.InternalServerErrorViewException;
import com.bit.microservices.service_approval.exceptions.views.NotFoundViewException;
import com.bit.microservices.service_approval.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.service_approval.mapper.MsEventApprovalMapper;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.CreateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.DeleteEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.MasterEventApproval.UpdateEventApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.RequestDetailDTO;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.response.MasterEventApproval.MsEventApprovalDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.ResponseDetailDTO;
import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.repository.IQMsEventApprovalRepository;
import com.bit.microservices.service_approval.repository.MsEventApprovalRepository;
import com.bit.microservices.service_approval.services.MsEventApprovalService;
import com.bit.microservices.service_approval.utils.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class MsEventApprovalServiceImpl implements MsEventApprovalService {

    private static final ServiceCodeEnum serviceCodeEnum = ServiceCodeEnum.EVENT_APPROVAL;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MsEventApprovalMapper msEventApprovalMapper;


    @Autowired
    private MsEventApprovalRepository msEventApprovalRepository;

    @Autowired
    private IQMsEventApprovalRepository iqMsEventApprovalRepository;


    @Override
    public Mono<List<ResultResponseDTO>> createMasterEventApproval(List<CreateEventApprovalRequestDTO> requestList) {
        String responseCode= HttpStatus.OK.value()+serviceCodeEnum.code+ FunctionServiceCodeEnum.CREATE.code+MessageCodeEnum.SUCCESS.code;
        BigDecimal responseCodeNumber = new BigDecimal(responseCode);
        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;

            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();
            }

            Transaction tx = session.beginTransaction();
            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            List<String> codeEventApprovalList = new ArrayList<>();

            Session finalSession = session;
            for (CreateEventApprovalRequestDTO createEventApprovalRequestDTO : requestList) {

                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setId(createEventApprovalRequestDTO.getCode());
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(responseCode,"EVENT APPROVAL CREATED SUCCESSFULLY");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
                codeEventApprovalList.add(createEventApprovalRequestDTO.getCode());
            }

            List<MsEventApproval> msEventApprovalList = this.msEventApprovalRepository.findByCodeIn(codeEventApprovalList);

            Map<String,Boolean> isNotDeletedEventApprovalMap = new HashMap<>();

            for (MsEventApproval msEventApproval : msEventApprovalList) {
                if(msEventApproval.getIsDeleted().equals(false)){
                    isNotDeletedEventApprovalMap.put(msEventApproval.getCode(),true);
                }
            }

            for (int i = 0; i < requestList.size(); i++) {
                System.out.println("get request");

                try {

                    CreateEventApprovalRequestDTO requestDTO = requestList.get(i);
                    if(!Objects.isNull(isNotDeletedEventApprovalMap.get(requestDTO.getCode())) && isNotDeletedEventApprovalMap.get(requestDTO.getCode())){
                        // if there is active event approval code exist
                        throw new BadRequestException("Save Failed : Event Approval Code Already Exist",eventApprovalResponseDTOList);
                    }else{

                        // if there is no active event approval exist
                        MsEventApproval msEventApproval = this.msEventApprovalMapper.dtoToEntity(requestDTO);

                        String uuid = UUID.randomUUID().toString();
                        msEventApproval.setId(requestDTO.getCode()+"~"+uuid);
                        eventApprovalResponseDTOList.get(i).setId(msEventApproval.getId());
                        finalSession.persist(msEventApproval);

                        finalSession.flush();
                        finalSession.clear();

                    }


                }
                catch (ResponseStatusException err){
                    ExceptionPrinter printer = new ExceptionPrinter(err);
                    log.error("Error : {}",printer.getMessage());
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();
                    throw err;
                }
                catch (Exception ex){
                    ExceptionPrinter printer = new ExceptionPrinter(ex);
                    log.error("Error : {}",printer.getMessage());
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();

                    throw new InternalServerErrorException("Save Failed : ",eventApprovalResponseDTOList);
                }
            }


            tx.commit();

            return eventApprovalResponseDTOList;
        });
    }

    @Override
    public Mono<List<ResultResponseDTO>> updateMasterEventApproval(List<UpdateEventApprovalRequestDTO> requestList) {

        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }

            Transaction tx = session.beginTransaction();

            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            Session finalSession = session;
            List<String> idMsEventApprovalList = new ArrayList<>();
            requestList.forEach((request)->{
                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"MASTER EVENT APPROVAL Updated Successfully");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
                idMsEventApprovalList.add(request.getId());
            });

            List<MsEventApproval> msEventApprovalList = this.msEventApprovalRepository.findByIdIn(idMsEventApprovalList);
            Map<String,MsEventApproval> msEventApprovalMap = new HashMap<>();
            msEventApprovalList.forEach((event)->{
                msEventApprovalMap.put(event.getId(),event);
            });
            for (int i = 0; i < requestList.size(); i++) {
                UpdateEventApprovalRequestDTO requestDTO = requestList.get(i);
                try {
                    if(requestDTO.getId().equals("0")){
                        throw new BadRequestException("Update Failed: Format Penomoran",eventApprovalResponseDTOList);
                    }
                    if(!Objects.isNull(msEventApprovalMap.get(requestDTO.getId()))){
                        MsEventApproval dataDb =  msEventApprovalMap.get(requestDTO.getId());
                        if(dataDb.getIsDeleted()){
//                        eventApprovalResponseDTOList.get(i).setMessageResponseDetail("Update Failed : Already Deleted, Cannot be Undo on ID : "+requestDTO.getId());
                            throw new BadRequestException("Update Failed : Already Deleted, Cannot be Undo",eventApprovalResponseDTOList);
                        }
                        if(!dataDb.getCode().equals(requestDTO.getCode())){
//                        eventApprovalResponseDTOList.get(i).setMessageResponseDetail("Update Failed : Inconsistent Code, Code Can Not Be Updated On ID : "+requestDTO.getId());
                            throw new BadRequestException("Update Failed : Inconsistent Code, Code Can Not Be Updated",eventApprovalResponseDTOList);
                        }

                        MsEventApproval msEventApproval = this.msEventApprovalMapper.dtoToEntity(requestDTO);

                        UtilService.copyProperties(msEventApproval,dataDb);
                        String uuid = dataDb.getId();
                        eventApprovalResponseDTOList.get(i).setId(requestDTO.getCode()+"~"+uuid);

                        finalSession.merge(dataDb);

                        finalSession.flush();
                        finalSession.clear();

                    }else{
//                    eventApprovalResponseDTOList.get(i).setMessageResponseDetail("Update Failed: Event Master Approval Update Failed on ID "+requestDTO.getId()+" Doesn`t Exist");
                        throw new NotFoundException("Update Failed: Event Master Approval Update Failed Doesnt Exist",eventApprovalResponseDTOList);
                    }

                }
                catch (ResponseStatusException err){
                    ExceptionPrinter printer = new ExceptionPrinter(err);
                    log.error("Error : {}",printer.getMessage());
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();
                    throw err;
                }
                catch (Exception ex){
                    ExceptionPrinter printer = new ExceptionPrinter(ex);
                    log.error("Error : {}",printer.getMessage());
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();

                    throw new InternalServerErrorException("EVENT APPROVAL Creation Failed: "+printer.getMessage(),eventApprovalResponseDTOList);
                }
            }


            tx.commit();

            return eventApprovalResponseDTOList;
        });
    }

    @Override
    public Mono<List<ResultResponseDTO>> deleteMasterEventApproval(List<DeleteEventApprovalRequestDTO> requestList) {
       return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
           Session session = null;
           try {
               session = sessionFactory.getCurrentSession();
           }catch (Exception err){
               session =sessionFactory.openSession();

           }

           Transaction tx = session.beginTransaction();

           List<String> idMsEventApprovalList = new ArrayList<>();
           Session finalSession = session;
           ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
           requestList.forEach((request)->{
               ResultResponseDTO responseDto = new ResultResponseDTO();
               responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
               responseDto.setId(request.getId());
               ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"MASTER EVENT APPROVAL Updated Successfully");
               responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
               eventApprovalResponseDTOList.add(responseDto);
               idMsEventApprovalList.add(request.getId());
           });

           List<MsEventApproval> msEventApprovalList = this.msEventApprovalRepository.findByIdIn(idMsEventApprovalList);
           Map<String,MsEventApproval> msEventApprovalMap = new HashMap<>();
           msEventApprovalList.forEach((event)->{
               msEventApprovalMap.put(event.getId(),event);
           });


           requestList.forEach((request)->{
               try{
                   if(!Objects.isNull(msEventApprovalMap.get(request.getId()))){
                       MsEventApproval dataDb = msEventApprovalMap.get(request.getId());
                       dataDb.setIsDeleted(true);
                       dataDb.setDeletedReason(request.getDeletedReason());
                       finalSession.merge(dataDb);
                       finalSession.flush();
                   }else{
                       throw new NotFoundException("Delete Failed: Event Master Approval Delete Failed Doesnt Exist",eventApprovalResponseDTOList);
                   }
               }
               catch (ResponseStatusException err){
                   ExceptionPrinter printer = new ExceptionPrinter(err);
                   log.error("Error : {}",printer.getMessage());
                   finalSession.clear();
                   if(tx.isActive()){
                       tx.rollback();
                   }
                   finalSession.close();
                   throw err;
               }
               catch (Exception ex){
                   ExceptionPrinter printer = new ExceptionPrinter(ex);
                   log.error("Error : {}",printer.getMessage());
                   finalSession.clear();
                   if(tx.isActive()){
                       tx.rollback();
                   }
                   finalSession.close();

                   throw new InternalServerErrorException("EVENT APPROVAL Deletion Failed: "+printer.getMessage(),eventApprovalResponseDTOList);
               }

           });

           finalSession.clear();
           tx.commit();
           return eventApprovalResponseDTOList;
       });
    }

    @Override
    public Mono<ViewPagingResponseDTO> getEventApproval(SearchRequestDTO requestDTO) {

        String responseCode= serviceCodeEnum.code+ FunctionServiceCodeEnum.GET_LIST.code;
        try {
            ViewPagingResponseDTO responseDTO =this.iqMsEventApprovalRepository.findEventApproval(requestDTO);
            responseDTO.responseCode = HttpStatus.OK.value()+responseCode+ MessageCodeEnum.SUCCESS;
            return Mono.just(responseDTO);
        }catch (BaseResponseCodeException err){
            err.setResponseCode(responseCode);
            throw err;
//                err.getStackTrace()[0].getclas
        }catch (Exception err){
            throw new InternalServerErrorViewException("Internal Server Error",new ArrayList<>(),MessageCodeEnum.UNKNOWN);
        }


    }

    @Override
    public Mono<ViewMainResponseDTO<MsEventApprovalDTO>> getSingleEventApproval(RequestDetailDTO requestDetailDTO) {
        Optional<MsEventApproval> msEventApproval =  this.msEventApprovalRepository.findById(requestDetailDTO.getId());
        MsEventApproval msEventApprovalData = msEventApproval.orElseThrow(()->new NotFoundViewException("Event Approval Tidak Ditemukan",new HashMap<>()));
        MsEventApprovalDTO dto = this.msEventApprovalMapper.entityToDto(msEventApprovalData);
        return Mono.just(new ViewMainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,00101230123,"Event Approval Get Successfully",dto));

    }
}
