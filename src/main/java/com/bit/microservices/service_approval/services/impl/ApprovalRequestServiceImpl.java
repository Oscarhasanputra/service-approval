package com.bit.microservices.service_approval.services.impl;

import com.bit.microservices.service_approval.entity.*;
import com.bit.microservices.service_approval.enums.ApprovalHistoryStatusEnum;
import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.exceptions.BadRequestException;
import com.bit.microservices.service_approval.exceptions.ExceptionPrinter;
import com.bit.microservices.service_approval.exceptions.InternalServerErrorException;
import com.bit.microservices.service_approval.exceptions.NotFoundException;
import com.bit.microservices.service_approval.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.service_approval.mapper.ApprovalHistoryMapper;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.AssigneeTransApprovalHistoryDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.CreateTransApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.DeleteTransApprovalHistoryRequestDTO;
import com.bit.microservices.service_approval.model.request.TransApprovalHistory.LevelTransApprovalHistoryDTO;
import com.bit.microservices.service_approval.model.response.ResponseDetailDTO;
import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.repository.ApprovalRequestLevelAssigneeTrcRepository;
import com.bit.microservices.service_approval.repository.ApprovalRequestLevelTrcRepository;
import com.bit.microservices.service_approval.repository.ApprovalRequestTrxRepository;
import com.bit.microservices.service_approval.repository.IQApprovalRequestRepository;
import com.bit.microservices.service_approval.services.ApprovalRequestService;
import com.bit.microservices.service_approval.utils.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Slf4j
public class ApprovalRequestServiceImpl implements ApprovalRequestService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ApprovalHistoryMapper approvalHistoryMapper;

    @Autowired
    private ApprovalRequestTrxRepository approvalRequestTrxRepository;

    @Autowired
    private ApprovalRequestLevelTrcRepository approvalRequestLevelTrcRepository;

    @Autowired
    private ApprovalRequestLevelAssigneeTrcRepository approvalRequestLevelAssigneeTrcRepository;

    @Autowired
    private IQApprovalRequestRepository iqApprovalRequestRepository;
    @Override
    public Mono<List<ResultResponseDTO>> createApprovalRequest(List<CreateTransApprovalRequestDTO> requestDTO) {

        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }
            Transaction tx = session.beginTransaction();
            Session finalSession = session;

            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            List<String> idApprovalHistoryList = new ArrayList<>();
            requestDTO.forEach(request->{
                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"APPROVAL HISTORY CREATED SUCCESSFULLY");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
                idApprovalHistoryList.add(request.getId());
            });
            List<ApprovalRequestTrx> approvalRequestTrxList = this.approvalRequestTrxRepository.findByIdIn(idApprovalHistoryList);
            System.out.println("create approval request");
            System.out.println(idApprovalHistoryList);
            System.out.println(this.approvalRequestTrxRepository.findAll());
            System.out.println(approvalRequestTrxList);
            Map<String, ApprovalRequestTrx> approvalHistoryTrxMap = new HashMap<>();
            approvalRequestTrxList.forEach((data)->{
                approvalHistoryTrxMap.put(data.getId(),data);
            });
            List<String> statusIncluded = Arrays.asList(ApprovalHistoryStatusEnum.NEW.status,ApprovalHistoryStatusEnum.WAITING_FOR_APPROVAL.status);
            for (int i = 0; i < requestDTO.size(); i++) {
                CreateTransApprovalRequestDTO request = requestDTO.get(i);
                try {
                    if(request.getId().isEmpty() || request.getId().equals("0")) {
                        throw new BadRequestException("Event History Approval Creation Failed : Format Penomoran Salah", eventApprovalResponseDTOList);
                    }

                    ApprovalRequestTrx approvalRequestTrx = this.approvalHistoryMapper.historyDtoToEntity(request);
                    if(!Objects.isNull(approvalHistoryTrxMap.get(request.getId()))){
                        System.out.println("id request approval history");
                        ApprovalRequestTrx dataDb = approvalHistoryTrxMap.get(request.getId());

                        System.out.println(dataDb);
                        if(dataDb.getCode().equals(request.getCode())
                                && dataDb.getIsDeleted().equals(false)
                                && statusIncluded.indexOf(dataDb.getStatus())>=0
                        ){
                            throw new BadRequestException("Event History Approval Creation Failed : Request Approval Already Exists", eventApprovalResponseDTOList);
                        }
                    }

                    approvalRequestTrx.setStatus(request.getStatus());

                    String idApprovalHistoryTrx = request.getId();
                    eventApprovalResponseDTOList.get(i).setId(idApprovalHistoryTrx);

                    approvalRequestTrx.setId(idApprovalHistoryTrx);
                    finalSession.persist(approvalRequestTrx);
                    for (LevelTransApprovalHistoryDTO levelTransApprovalHistoryDTO : request.getLevel()) {
                        if(levelTransApprovalHistoryDTO.getId().isEmpty() || levelTransApprovalHistoryDTO.getId().equals("0")){
                            ApprovalRequestLevelTrc approvalRequestLevelTrc = this.approvalHistoryMapper.levelHistoryDtoToEntity(levelTransApprovalHistoryDTO);
                            approvalRequestLevelTrc.setTrxApprovalrequestId(idApprovalHistoryTrx);

                            String idLevelApprovalHistory = UUID.randomUUID().toString();
                            approvalRequestLevelTrc.setId(idLevelApprovalHistory);
                            finalSession.persist(approvalRequestLevelTrc);

                            for (AssigneeTransApprovalHistoryDTO assigneeTransApprovalHistoryDTO : levelTransApprovalHistoryDTO.getAssignee()) {
                                if(assigneeTransApprovalHistoryDTO.getId().isEmpty() || assigneeTransApprovalHistoryDTO.getId().equals("0")){
                                    ApprovalRequestLevelAssigneeTrc approvalRequestLevelAssigneeTrc = this.approvalHistoryMapper.levelHistoryAssigneeDtoToEntity(assigneeTransApprovalHistoryDTO);
                                    approvalRequestLevelAssigneeTrc.setTrcApprovalrequestlevelId(idLevelApprovalHistory);
                                    String idLevelAssigneeApprovalHistory = UUID.randomUUID().toString();
                                    approvalRequestLevelAssigneeTrc.setId(idLevelAssigneeApprovalHistory);
                                    finalSession.persist(approvalRequestLevelAssigneeTrc);
                                }else{
                                    throw new BadRequestException("Event History Approval Creation Failed : Format Penomoran Salah",eventApprovalResponseDTOList);
                                }

                            }

                            finalSession.flush();
                            finalSession.clear();
                        }else{
                            throw new BadRequestException("Event History Approval Creation Failed : Format Penomoran Salah",eventApprovalResponseDTOList);
                        }

                    }



                }catch (ResponseStatusException err){
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();
                    throw err;
                }
                catch (Exception err){
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();
                    throw new InternalServerErrorException("Event History Approval Creation Failed : "+err.toString(),eventApprovalResponseDTOList);

                }
            }

            tx.commit();
            finalSession.clear();

            return eventApprovalResponseDTOList;
        });
    }

    @Override
    public Mono<List<ResultResponseDTO>> updateApprovalRequest(List<CreateTransApprovalRequestDTO> requestDTO) {
        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }
            Transaction tx = session.beginTransaction();
            Session finalSession = session;

            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            List<String> idApprovalHistoryList = new ArrayList<>();
            List<String> idLevelApprovalList = new ArrayList<>();
            List<String> idAssigneeApprovalList = new ArrayList<>();
            requestDTO.forEach((request)->{
                idApprovalHistoryList.add(request.getId());

                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"APPROVAL HISTORY Updated Successfully");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
                request.getLevel().forEach((level)->{
                    idLevelApprovalList.add(level.getId());
                    for (AssigneeTransApprovalHistoryDTO assigneeTransApprovalHistoryDTO : level.getAssignee()) {
                        idAssigneeApprovalList.add(assigneeTransApprovalHistoryDTO.getId());
                    }
                });
            });

            List<ApprovalRequestTrx> approvalRequestTrxList = this.approvalRequestTrxRepository.findByIdIn(idApprovalHistoryList);
            List<ApprovalRequestLevelTrc> approvalRequestLevelTrcList = this.approvalRequestLevelTrcRepository.findByIdIn(idLevelApprovalList);
            List<ApprovalRequestLevelAssigneeTrc> approvalRequestLevelAssigneeTrcList = this.approvalRequestLevelAssigneeTrcRepository.findByIdIn(idAssigneeApprovalList);


            Map<String, ApprovalRequestTrx> approvalHistoryMap = new HashMap<>();
            Map<String, ApprovalRequestLevelTrc> approvalHistoryLevelMap = new HashMap<>();
            Map<String, ApprovalRequestLevelAssigneeTrc> approvalHistoryLevelAssigneeMap = new HashMap<>();

            approvalRequestTrxList.forEach(data->{
                approvalHistoryMap.put(data.getId(),data);
            });
            approvalRequestLevelTrcList.forEach(data->{
                approvalHistoryLevelMap.put(data.getTrxApprovalrequestId()+"."+data.getId(),data);
            });
            approvalRequestLevelAssigneeTrcList.forEach((data)->{
                approvalHistoryLevelAssigneeMap.put(data.getTrcApprovalrequestlevelId()+"."+data.getId(),data);
            });


            for (int i = 0; i < requestDTO.size(); i++) {
                CreateTransApprovalRequestDTO request = requestDTO.get(i);
                try {
                    if(request.getId().isEmpty() || request.getId().equals("0")){
                        throw new BadRequestException("Event Request Approval Update Failed: Format Penomoran Salah",eventApprovalResponseDTOList);
                    }

                    if(!Objects.isNull(approvalHistoryMap.get(request.getId()))){
                        ApprovalRequestTrx dataDbApprovalHistory = approvalHistoryMap.get(request.getId());
                        if(!dataDbApprovalHistory.getCode().equals(request.getCode())){
                            throw new BadRequestException("Event Request Approval Update Failed: Inconsistent Code, Code Can Not Be Updated",eventApprovalResponseDTOList);
                        }
                        if(dataDbApprovalHistory.getIsDeleted()){
                            throw new BadRequestException("Event Request Approval Update Failed: Already Deleted",eventApprovalResponseDTOList);
                        }

                        ApprovalRequestTrx approvalRequestTrx = this.approvalHistoryMapper.historyDtoToEntity(request);
                        String idApprovalHistoryTrx = dataDbApprovalHistory.getId();
                        eventApprovalResponseDTOList.get(i).setId(idApprovalHistoryTrx);
                        UtilService.copyProperties(approvalRequestTrx,dataDbApprovalHistory);
                        finalSession.merge(dataDbApprovalHistory);
                        for (LevelTransApprovalHistoryDTO levelTransApprovalHistoryDTO : request.getLevel()) {
                            String idLevelApprovalHistory = null;
                            if(levelTransApprovalHistoryDTO.getId().isEmpty() || levelTransApprovalHistoryDTO.getId().equals("0")){
                                ApprovalRequestLevelTrc approvalRequestLevelTrc = this.approvalHistoryMapper.levelHistoryDtoToEntity(levelTransApprovalHistoryDTO);
                                approvalRequestLevelTrc.setTrxApprovalrequestId(idApprovalHistoryTrx);

                                idLevelApprovalHistory = UUID.randomUUID().toString();
                                approvalRequestLevelTrc.setId(idLevelApprovalHistory);
                                finalSession.persist(approvalRequestLevelTrc);
                            }
                            else{
                                if(!Objects.isNull(approvalHistoryLevelMap.get(request.getId()+"."+levelTransApprovalHistoryDTO.getId()))){
                                    ApprovalRequestLevelTrc dataDbApprovalHistoryLevel = approvalHistoryLevelMap.get(request.getId()+"."+levelTransApprovalHistoryDTO.getId());

                                    ApprovalRequestLevelTrc approvalRequestLevelTrc = this.approvalHistoryMapper.levelHistoryDtoToEntity(levelTransApprovalHistoryDTO);
                                    UtilService.copyProperties(approvalRequestLevelTrc,dataDbApprovalHistoryLevel);

                                    idLevelApprovalHistory = dataDbApprovalHistoryLevel.getId();

                                    finalSession.merge(dataDbApprovalHistoryLevel);


                                }else{
//                                eventApprovalResponseDTOList.get(i).setMessageResponseDetail("Event Request Approval Update Failed on Approval History Level Id : "+levelTransApprovalHistoryDTO.getId()+" Doesn`t Exist");
                                    throw new BadRequestException("Event Request Approval Update Failed: Approval History Level Id Not Exists",eventApprovalResponseDTOList);
                                }
                            }

                            for (AssigneeTransApprovalHistoryDTO assigneeTransApprovalHistoryDTO : levelTransApprovalHistoryDTO.getAssignee()) {
                                if(assigneeTransApprovalHistoryDTO.getId().isEmpty() || assigneeTransApprovalHistoryDTO.getId().equals("0")){
                                    ApprovalRequestLevelAssigneeTrc approvalRequestLevelAssigneeTrc = this.approvalHistoryMapper.levelHistoryAssigneeDtoToEntity(assigneeTransApprovalHistoryDTO);
                                    approvalRequestLevelAssigneeTrc.setTrcApprovalrequestlevelId(idLevelApprovalHistory);
                                    String idLevelAssigneeApprovalHistory = UUID.randomUUID().toString();
                                    approvalRequestLevelAssigneeTrc.setId(idLevelAssigneeApprovalHistory);
                                    finalSession.persist(approvalRequestLevelAssigneeTrc);
                                }else{
                                    if(!Objects.isNull(approvalHistoryLevelAssigneeMap.get(levelTransApprovalHistoryDTO.getId()+"."+assigneeTransApprovalHistoryDTO.getId()))){
                                        ApprovalRequestLevelAssigneeTrc dataDbApprovalAssignee =approvalHistoryLevelAssigneeMap.get(levelTransApprovalHistoryDTO.getId()+"."+assigneeTransApprovalHistoryDTO.getId());
                                        ApprovalRequestLevelAssigneeTrc approvalRequestLevelAssigneeTrc = this.approvalHistoryMapper.levelHistoryAssigneeDtoToEntity(assigneeTransApprovalHistoryDTO);
                                        UtilService.copyProperties(approvalRequestLevelAssigneeTrc,dataDbApprovalAssignee);

                                        finalSession.merge(dataDbApprovalAssignee);
                                    }else{
//                                    eventApprovalResponseDTOList.get(i).setMessageResponseDetail("Event Request Approval Update Failed on Approval History Level Assignee Id : "+assigneeTransApprovalHistoryDTO.getId()+" Doesn`t Exist");
                                        throw new BadRequestException("Event Request Approval Update Failed: Approval Request Level Assignee Id Not Exists",eventApprovalResponseDTOList);
                                    }
                                }


                            }

                            finalSession.flush();
                            finalSession.clear();
                        }


                    }else{
//                    eventApprovalResponseDTOList.get(i).setMessageResponseDetail("Event Request Approval Update Failed on Approval History Id : "+request.getId()+" Doesn`t Exist");
                        throw new BadRequestException("Event Request Approval Update Failed Doesnt Exist",eventApprovalResponseDTOList);
                    }

                }
                catch (ResponseStatusException err){
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();
                    throw err;
                }
                catch (Exception err){
                    finalSession.clear();
                    if(tx.isActive()){
                        tx.rollback();
                    }
                    finalSession.close();
                    throw new InternalServerErrorException("Event Request Approval Update Failed",eventApprovalResponseDTOList);

                }
            }

            tx.commit();
            finalSession.clear();

            return eventApprovalResponseDTOList;
        });
    }

    @Override
    public Mono<List<ResultResponseDTO>> deleteApprovalRequest(List<DeleteTransApprovalHistoryRequestDTO> requestList) {
        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }

            Transaction tx = session.beginTransaction();

            List<String> idApprovalHistoryList = new ArrayList<>();
            Session finalSession = session;
            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            requestList.forEach((request)->{
                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"MASTER EVENT APPROVAL Updated Successfully");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
                idApprovalHistoryList.add(request.getId());
            });
            List<ApprovalRequestTrx> approvalRequestTrxList = this.approvalRequestTrxRepository.findByIdIn(idApprovalHistoryList);
            Map<String, ApprovalRequestTrx> approvalHistoryTrxMap = new HashMap<>();
            approvalRequestTrxList.forEach((config)->{
                approvalHistoryTrxMap.put(config.getId(),config);
            });

            requestList.forEach((request)->{
                try{
                    if(!Objects.isNull(approvalHistoryTrxMap.get(request.getId()))){

                        ApprovalRequestTrx dataDb = approvalHistoryTrxMap.get(request.getId());

                        dataDb.setIsDeleted(true);
                        dataDb.setDeletedReason(request.getDeletedReason());
                        finalSession.merge(dataDb);
                        finalSession.flush();
                    }else{
                        throw new NotFoundException("Delete Failed: Approval History Delete Failed Doesnt Exist",eventApprovalResponseDTOList);
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

                    throw new InternalServerErrorException("Approval History Deletion Failed: "+printer.getMessage(),eventApprovalResponseDTOList);
                }

            });
            finalSession.clear();
            tx.commit();
            return eventApprovalResponseDTOList;
        });

    }

    @Override
    public Mono<ViewPagingResponseDTO> getApprovalRequest(SearchRequestDTO requestDTO) {
        return Mono.just(this.iqApprovalRequestRepository.findApprovalRequest(requestDTO));
    }
}
