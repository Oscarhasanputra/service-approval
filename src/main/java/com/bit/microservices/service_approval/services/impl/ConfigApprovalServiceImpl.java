package com.bit.microservices.service_approval.services.impl;

import com.bit.microservices.service_approval.entity.*;
import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.exceptions.BadRequestException;
import com.bit.microservices.service_approval.exceptions.ExceptionPrinter;
import com.bit.microservices.service_approval.exceptions.InternalServerErrorException;
import com.bit.microservices.service_approval.exceptions.NotFoundException;
import com.bit.microservices.service_approval.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.service_approval.mapper.ConfigApprovalMapper;
import com.bit.microservices.service_approval.model.request.SearchRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.AssigneeSubEventTransConfigDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.CreateTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.DeleteTransConfigApprovalRequestDTO;
import com.bit.microservices.service_approval.model.request.TransConfigApproval.WhiteListUserConfigApprovalDTO;
import com.bit.microservices.service_approval.model.response.ResponseDetailDTO;
import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewPagingResponseDTO;
import com.bit.microservices.service_approval.repository.*;
import com.bit.microservices.service_approval.services.ConfigApprovalService;
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
public class ConfigApprovalServiceImpl implements ConfigApprovalService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ConfigApprovalMapper configApprovalMapper;

    @Autowired
    private MsEventApprovalRepository msEventApprovalRepository;

    @Autowired
    private ConfigApprovalSubEventTrcRepository configApprovalSubEventTrcRepository;

    @Autowired
    private ConfigApprovalSubEventAssigneeTrcRepository configApprovalSubEventAssigneeTrcRepository;

    @Autowired
    private ConfigApprovalTrxRepository configApprovalTrxRepository;

    @Autowired
    private IQConfigApprovalRepository iqConfigApprovalRepository;

    @Override
    public Mono<List<ResultResponseDTO>> createConfigApproval(List<CreateTransConfigApprovalRequestDTO> requestDTO) {
        return ReactiveSecurityContextHolderData.assignContextData().flatMap((data)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }

            Transaction tx = session.beginTransaction();
            List<String> eventApprovalIdList = requestDTO.stream().map((request)->request.getEventApprovalId()).toList();
            List<MsEventApproval> msEventApprovalList = this.msEventApprovalRepository.findByIdIn(eventApprovalIdList);

            Map<String,MsEventApproval> mapMsEventApproval = new HashMap<>();
            for (MsEventApproval msEventApproval : msEventApprovalList) {
                mapMsEventApproval.put(msEventApproval.getId(),msEventApproval);
            }

            Session finalSession = session;

            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            List<String> idConfigApprovalList= new ArrayList<>();
            requestDTO.forEach(request->{
                idConfigApprovalList.add(request.getId());
                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"EVENT CONFIG APPROVAL CREATED SUCCESSFULLY");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
            });
            List<ConfigApprovalTrx> configApprovalTrxList = this.configApprovalTrxRepository.findByIdIn(idConfigApprovalList);
            Map<String,ConfigApprovalTrx> configApprovalTrxMap = new HashMap<>();
            configApprovalTrxList.forEach((configApproval)->{
                configApprovalTrxMap.put(configApproval.getId(),configApproval);
            });
            for (int i = 0; i < requestDTO.size(); i++) {
                CreateTransConfigApprovalRequestDTO request= requestDTO.get(i);

                //check if event approval id exist in db to ensure relation db

                try{
                    if(request.getId().equals("0")){
                        throw new BadRequestException("Save Failed: Format Penomoran Salah",eventApprovalResponseDTOList);
                    }

                    if(!Objects.isNull(mapMsEventApproval.get(request.getEventApprovalId()))){
                        if (!Objects.isNull(configApprovalTrxMap.get(request.getId()))){
                            ConfigApprovalTrx dataDb =configApprovalTrxMap.get(request.getId());

                            if(dataDb.getMsEventapprovalCode().equals(request.getEventApprovalCode())
                                    && dataDb.getEffectiveDate().equals(request.getEffectiveDate().toLocalDateTime())
                                    && dataDb.getIsDeleted().equals(false)
                            ){
                                throw new BadRequestException("Save Failed: Config Already Exists",eventApprovalResponseDTOList);
                            }
                        }
                        ConfigApprovalTrx configApprovalTrx = this.configApprovalMapper.dtoToEntityConfigApproval(request);
                        String idConfigApproval = request.getId();
                        configApprovalTrx.setId(idConfigApproval);

                        finalSession.persist(configApprovalTrx);

                        eventApprovalResponseDTOList.get(i).setId(idConfigApproval);
                        request.getSubevent().forEach((subEvent)->{

                            ConfigApprovalSubEventTrc subEventTrc = this.configApprovalMapper.subEventDtoToentityConfigApproval(subEvent);

                            if(!(subEvent.getId().isEmpty() || subEvent.getId().equals("0"))){
                                throw new BadRequestException("Save Failed: Format Penomoran Salah",eventApprovalResponseDTOList);
                            }


                            String idSubEventConfigApproval = UUID.randomUUID().toString();
                            subEventTrc.setId(idSubEventConfigApproval);
                            subEventTrc.setTrxConfigapprovalId(idConfigApproval);
                            finalSession.persist(subEventTrc);
                            for (AssigneeSubEventTransConfigDTO assigneeSubEventTransConfigDTO : subEvent.getAssignee()) {

                                if(!(assigneeSubEventTransConfigDTO.getId().isEmpty() || assigneeSubEventTransConfigDTO.getId().equals("0"))){
                                    throw new BadRequestException("Save Failed: Format Penomoran Salah",eventApprovalResponseDTOList);
                                }
                                ConfigApprovalSubEventAssigneeTrc configApprovalSubEventAssigneeTrc= this.configApprovalMapper.subEventAssigneeDtoToEntityAssigneeApproval(assigneeSubEventTransConfigDTO);
                                configApprovalSubEventAssigneeTrc.setTrcConfigapprovalsubeventId(idSubEventConfigApproval);
                                String idAssigneeSubEventConfigApproval = UUID.randomUUID().toString();
                                configApprovalSubEventAssigneeTrc.setId(idAssigneeSubEventConfigApproval);
                                finalSession.persist(configApprovalSubEventAssigneeTrc);

                            }
                            for (WhiteListUserConfigApprovalDTO whiteListUserConfigApprovalDTO : subEvent.getWhitelistuser()) {

                                if(!(whiteListUserConfigApprovalDTO.getId().isEmpty() || whiteListUserConfigApprovalDTO.getId().equals("0"))){
                                    throw new BadRequestException("Save Failed: Format Penomoran Salah",eventApprovalResponseDTOList);
                                }
                                ConfigApprovalSubEventWhiteListUserTrc configApprovalSubEventWhiteListUserTrc= this.configApprovalMapper.subEventWhiteListUserDtoToEntityWhiteListUserApproval(whiteListUserConfigApprovalDTO);

                                configApprovalSubEventWhiteListUserTrc.setTrcConfigapprovalsubeventId(idSubEventConfigApproval);
                                System.out.println("config approval whitelist user dto");
                                System.out.println(configApprovalSubEventWhiteListUserTrc);
                                String idAssigneeSubEventConfigApproval = UUID.randomUUID().toString();
                                configApprovalSubEventWhiteListUserTrc.setId(idAssigneeSubEventConfigApproval);
                                finalSession.persist(configApprovalSubEventWhiteListUserTrc);

                            }

                            finalSession.flush();
                            finalSession.clear();

                        });


                    }else{

                        throw new NotFoundException("Save Failed: Event Approval ID Doesnt Exist",eventApprovalResponseDTOList);
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
                    throw new InternalServerErrorException("Save Failed : "+err.toString(),eventApprovalResponseDTOList);
                }

            }

            tx.commit();
            finalSession.clear();
            return Mono.just(eventApprovalResponseDTOList);
        });

    }

    @Override
    public Mono<List<ResultResponseDTO>> updateConfigApproval(List<CreateTransConfigApprovalRequestDTO> requestDTO) {
        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }

            Transaction tx = session.beginTransaction();
            List<String> eventApprovalIdList = requestDTO.stream().map((request)->request.getEventApprovalId()).toList();
            List<MsEventApproval> msEventApprovalList = this.msEventApprovalRepository.findByIdIn(eventApprovalIdList);

            Map<String,MsEventApproval> mapMsEventApproval = new HashMap<>();
            for (MsEventApproval msEventApproval : msEventApprovalList) {
                mapMsEventApproval.put(msEventApproval.getId(),msEventApproval);
            }

            Session finalSession = session;

            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();

            List<String> idConfigApprovalList = new ArrayList<>();
            List<String> idApprovalSubeventList = new ArrayList<>();
            List<String> idApprovalSubeventAssigneeList = new ArrayList<>();
            requestDTO.forEach((request)->{
                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"EVENT CONFIG APPROVAL CREATED SUCCESSFULLY");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);

                idConfigApprovalList.add(request.getId());
                request.getSubevent().forEach((subEvent)->{
                    idApprovalSubeventList.add(subEvent.getId());
                    for (AssigneeSubEventTransConfigDTO subEventAssignee : subEvent.getAssignee()) {
                        idApprovalSubeventAssigneeList.add(subEventAssignee.getId());
                    }
                });
            });

            List<ConfigApprovalTrx> configApprovalTrxList = this.configApprovalTrxRepository.findByIdIn(idConfigApprovalList);
            List<ConfigApprovalSubEventTrc> configApprovalSubeventTrcList = this.configApprovalSubEventTrcRepository.findByIdIn(idApprovalSubeventList);
            List<ConfigApprovalSubEventAssigneeTrc> configApprovalSubeventAssigneeTrcList = this.configApprovalSubEventAssigneeTrcRepository.findByIdIn(idApprovalSubeventAssigneeList);


            Map<String,ConfigApprovalTrx> configApprovalMap = new HashMap<>();
            Map<String,ConfigApprovalSubEventTrc> configApprovalSubeventMap = new HashMap<>();
            Map<String,ConfigApprovalSubEventAssigneeTrc> configApprovalSubeventAssigneeMap = new HashMap<>();
            Map<String,ConfigApprovalSubEventWhiteListUserTrc> configApprovalSubeventWhitelistMap = new HashMap<>();

            configApprovalTrxList.forEach(data->{
                configApprovalMap.put(data.getId(),data);
            });
            configApprovalSubeventTrcList.forEach(data->{
                configApprovalSubeventMap.put(data.getTrxConfigapprovalId()+"."+data.getId(),data);
            });
            configApprovalSubeventAssigneeTrcList.forEach((data)->{
                configApprovalSubeventAssigneeMap.put(data.getTrcConfigapprovalsubeventId()+"."+data.getId(),data);
            });
            for(int i=0;i<requestDTO.size();i++){
                CreateTransConfigApprovalRequestDTO request= requestDTO.get(i);
                //check if event approval id exist in db to ensure relation db

                int finalI = i;
                try{
                    if(!Objects.isNull(mapMsEventApproval.get(request.getEventApprovalId()))){
                        if(request.getId().isEmpty() || request.getId().equals("0")){
                            throw new BadRequestException("Event Config Approval Update Failed: Format Penomoran Salah",eventApprovalResponseDTOList);

                        }

                        if(!Objects.isNull(configApprovalMap.get(request.getId()))){
                            ConfigApprovalTrx dataDbConfigApproval = configApprovalMap.get(request.getId());
                            if(!(dataDbConfigApproval.getMsEventapprovalCode().equals(request.getEventApprovalCode())
                                    && dataDbConfigApproval.getEffectiveDate().isEqual(request.getEffectiveDate().toLocalDateTime()))
                            ){
                                throw new BadRequestException("Event Config Approval Update Failed: Inconsistent Code, Code Can Not Be Updated",eventApprovalResponseDTOList);

                            }
                            if(dataDbConfigApproval.getIsDeleted()){
                                throw new BadRequestException("Event Config Approval Update Failed: Already Deleted",eventApprovalResponseDTOList);
                            }

                            ConfigApprovalTrx configApprovalTrx = this.configApprovalMapper.dtoToEntityConfigApproval(request);
                            String idConfigApproval = dataDbConfigApproval.getId();

                            UtilService.copyProperties(configApprovalTrx,dataDbConfigApproval);
                            eventApprovalResponseDTOList.get(i).setId(idConfigApproval);
                            finalSession.merge(dataDbConfigApproval);

                            request.getSubevent().forEach((subEvent)->{
                                String idSubEventConfigApproval = null;
                                if(subEvent.getId().isEmpty() || subEvent.getId().equals("0")){
                                    //add record subevent
                                    ConfigApprovalSubEventTrc subEventTrc = this.configApprovalMapper.subEventDtoToentityConfigApproval(subEvent);

                                    idSubEventConfigApproval = UUID.randomUUID().toString();
                                    subEventTrc.setId(idSubEventConfigApproval);
                                    subEventTrc.setTrxConfigapprovalId(idConfigApproval);
                                    finalSession.persist(subEventTrc);

                                }else{
                                    //update record subevent
                                    if(!Objects.isNull(configApprovalSubeventMap.get(request.getId()+"."+subEvent.getId()))){
                                        ConfigApprovalSubEventTrc dataDbSubEventApproval = configApprovalSubeventMap.get(request.getId()+"."+subEvent.getId());
                                        idSubEventConfigApproval = dataDbSubEventApproval.getId();
                                        ConfigApprovalSubEventTrc subEventTrc = this.configApprovalMapper.subEventDtoToentityConfigApproval(subEvent);
                                        UtilService.copyProperties(subEventTrc,dataDbSubEventApproval);
                                        finalSession.merge(dataDbSubEventApproval);

                                    }else{

//                                    eventApprovalResponseDTOList.get(finalI).setMessageResponseDetail("Event Config Approval Update Failed on Approval Config Subevent Id : "+subEvent.getId()+" Doesn`t Exist");
                                        throw new BadRequestException("Event Config Approval Update Failed: Config Aporoval Subevent Not Exist",eventApprovalResponseDTOList);
                                    }
                                    for (AssigneeSubEventTransConfigDTO assigneeSubEventTransConfigDTO : subEvent.getAssignee()) {
                                        if(assigneeSubEventTransConfigDTO.getId().isEmpty() || assigneeSubEventTransConfigDTO.getId().equals("0")){
                                            ConfigApprovalSubEventAssigneeTrc configApprovalSubEventAssigneeTrc= this.configApprovalMapper.subEventAssigneeDtoToEntityAssigneeApproval(assigneeSubEventTransConfigDTO);

                                            configApprovalSubEventAssigneeTrc.setTrcConfigapprovalsubeventId(idSubEventConfigApproval);
                                            String idAssigneeSubEventConfigApproval = UUID.randomUUID().toString();
                                            configApprovalSubEventAssigneeTrc.setId(idAssigneeSubEventConfigApproval);
                                            finalSession.persist(configApprovalSubEventAssigneeTrc);
                                        }else{
                                            if(!Objects.isNull(configApprovalSubeventAssigneeMap.get(subEvent.getId()+"."+assigneeSubEventTransConfigDTO.getId()))){
                                                ConfigApprovalSubEventAssigneeTrc dataDbAssigneeTrc = configApprovalSubeventAssigneeMap.get(subEvent.getId()+"."+assigneeSubEventTransConfigDTO.getId());

                                                ConfigApprovalSubEventAssigneeTrc configApprovalSubEventAssigneeTrc= this.configApprovalMapper.subEventAssigneeDtoToEntityAssigneeApproval(assigneeSubEventTransConfigDTO);
                                                UtilService.copyProperties(configApprovalSubEventAssigneeTrc,dataDbAssigneeTrc);

                                                finalSession.merge(dataDbAssigneeTrc);
                                            }else{
//                                        eventApprovalResponseDTOList.get(finalI).setMessageResponseDetail("Event Config Approval Update Failed on Approval Config Subevent Assignee Id : "+assigneeSubEventTransConfigDTO.getId()+" Doesn`t Exist");
                                                throw new BadRequestException("Event Config Approval Update Failed: Config Approval Subevent Assignee Not Exists",eventApprovalResponseDTOList);
                                            }
                                        }
                                    }

                                    for (WhiteListUserConfigApprovalDTO whiteListUserConfigApprovalDTO : subEvent.getWhitelistuser()) {

                                        if(whiteListUserConfigApprovalDTO.getId().isEmpty() || whiteListUserConfigApprovalDTO.getId().equals("0")){
                                            ConfigApprovalSubEventWhiteListUserTrc configApprovalSubEventWhiteListUserTrc= this.configApprovalMapper.subEventWhiteListUserDtoToEntityWhiteListUserApproval(whiteListUserConfigApprovalDTO);

                                            configApprovalSubEventWhiteListUserTrc.setTrcConfigapprovalsubeventId(idSubEventConfigApproval);
                                            String idWhiteListConfigApproval = UUID.randomUUID().toString();
                                            configApprovalSubEventWhiteListUserTrc.setId(idWhiteListConfigApproval);
                                            finalSession.persist(configApprovalSubEventWhiteListUserTrc);
                                        }else{
                                            if(!Objects.isNull(configApprovalSubeventAssigneeMap.get(subEvent.getId()+"."+whiteListUserConfigApprovalDTO.getId()))){
                                                ConfigApprovalSubEventAssigneeTrc dataDbAssigneeTrc = configApprovalSubeventAssigneeMap.get(subEvent.getId()+"."+whiteListUserConfigApprovalDTO.getId());

                                                ConfigApprovalSubEventWhiteListUserTrc configApprovalSubEventWhiteListUserTrc= this.configApprovalMapper.subEventWhiteListUserDtoToEntityWhiteListUserApproval(whiteListUserConfigApprovalDTO);
                                                UtilService.copyProperties(configApprovalSubEventWhiteListUserTrc,dataDbAssigneeTrc);

                                                finalSession.merge(dataDbAssigneeTrc);
                                            }else{
//                                              eventApprovalResponseDTOList.get(finalI).setMessageResponseDetail("Event Config Approval Update Failed on Approval Config Subevent Assignee Id : "+assigneeSubEventTransConfigDTO.getId()+" Doesn`t Exist");
                                                throw new BadRequestException("Event Config Approval Update Failed: Config Approval Subevent Whitelist User Not Exists",eventApprovalResponseDTOList);
                                            }
                                        }
//                                    if(!(whiteListUserConfigApprovalDTO.getId().isEmpty() || whiteListUserConfigApprovalDTO.getId().equals("0"))){
//                                        throw new BadRequestException("Save Failed: Format Penomoran Salah 4",eventApprovalResponseDTOList);
//                                    }
//                                    ConfigApprovalSubEventWhiteListUserTrc configApprovalSubEventWhiteListUserTrc= this.configApprovalMapper.subEventWhiteListUserDtoToEntityWhiteListUserApproval(whiteListUserConfigApprovalDTO);
//                                    configApprovalSubEventWhiteListUserTrc.setTrcConfigapprovalsubeventId(idSubEventConfigApproval);
//                                    String idAssigneeSubEventConfigApproval = UUID.randomUUID().toString();
//                                    configApprovalSubEventWhiteListUserTrc.setId(idAssigneeSubEventConfigApproval);
//                                    finalSession.persist(configApprovalSubEventWhiteListUserTrc);

                                    }
                                    finalSession.flush();
                                    finalSession.clear();
                                }





                            });

                        }
                        else{
//                        eventApprovalResponseDTOList.get(finalI).setMessageResponseDetail("Event Config Approval Update Failed on Approval Config Id : "+request.getId()+" Doesn`t Exist");
                            throw new NotFoundException("Event Config Approval Update Failed: Config Approval Not Exist",eventApprovalResponseDTOList);
                        }
                    }else{

//                    eventApprovalResponseDTOList.get(finalI).setMessageResponseDetail("Event Config Approval Update Failed on Approval Event ID : "+request.getEventApprovalId()+" Doesn`t Exist");
                        throw new NotFoundException("Event Config Approval Update Failed Event Approval Not Exist",eventApprovalResponseDTOList);

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
                    throw new InternalServerErrorException("Event Config Approval Updated Failed : "+err.toString(),eventApprovalResponseDTOList);
                }
            }

            tx.commit();
            finalSession.clear();
            return eventApprovalResponseDTOList;
        });

    }

    @Override
    public Mono<List<ResultResponseDTO>> deleteConfigApproval(List<DeleteTransConfigApprovalRequestDTO> requestList) {
        return ReactiveSecurityContextHolderData.assignContextData().map((userData)->{
            Session session = null;
            try {
                session = sessionFactory.getCurrentSession();
            }catch (Exception err){
                session =sessionFactory.openSession();

            }

            Transaction tx = session.beginTransaction();

            List<String> idConfigApprovalList = new ArrayList<>();
            Session finalSession = session;
            ResultListDTO<ResultResponseDTO> eventApprovalResponseDTOList = new ResultListDTO<ResultResponseDTO>();
            requestList.forEach((request)->{
                ResultResponseDTO responseDto = new ResultResponseDTO();
                responseDto.setStatusDetail(ResponseStatusEnum.SUCCESS.responseMessage);
                responseDto.setId(request.getId());
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.SUCCESS.responseCode,"MASTER EVENT APPROVAL Updated Successfully");
                responseDto.setResponseDetail(Arrays.asList(responseDetailDTO));
                eventApprovalResponseDTOList.add(responseDto);
                idConfigApprovalList.add(request.getId());
            });
            List<ConfigApprovalTrx> configApprovalTrxList = this.configApprovalTrxRepository.findByIdIn(idConfigApprovalList);
            Map<String,ConfigApprovalTrx> configApprovalTrxMap = new HashMap<>();
            configApprovalTrxList.forEach((config)->{
                configApprovalTrxMap.put(config.getId(),config);
            });

            requestList.forEach((request)->{
                try{
                    if(!Objects.isNull(configApprovalTrxMap.get(request.getId()))){

                        ConfigApprovalTrx dataDb = configApprovalTrxMap.get(request.getId());
                        dataDb.setIsDeleted(true);
                        dataDb.setDeletedReason(request.getDeletedReason());
                        finalSession.merge(dataDb);
                        finalSession.flush();
                    }else{
                        throw new NotFoundException("Delete Failed: Config Approval Delete Failed Doesnt Exist",eventApprovalResponseDTOList);
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

                    throw new InternalServerErrorException("Config Approval Deletion Failed: "+printer.getMessage(),eventApprovalResponseDTOList);
                }

            });
            finalSession.clear();
            tx.commit();
            return eventApprovalResponseDTOList;
        });
    }

    @Override
    public Mono<ViewPagingResponseDTO> getConfigApproval(SearchRequestDTO requestDTO) {

        return Mono.just(this.iqConfigApprovalRepository.findConfigApproval(requestDTO));
    }
}
