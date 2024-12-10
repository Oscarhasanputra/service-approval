package com.bit.microservices.service_approval.exceptions.handler;

import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.exceptions.*;
import com.bit.microservices.service_approval.model.response.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.*;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final MainResponseDTO<Object> handleBadRequestException(BadRequestException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        List<ResultResponseDTO> responseDetailListDTOS = ex.getData();
        if(!Objects.isNull(responseDetailListDTOS)){
            responseDetailListDTOS.stream().forEach((detail)->{
                detail.setStatusDetail(ResponseStatusEnum.FAILED.responseMessage);
                if(Objects.isNull(detail.getMessageResponseDetail()) || detail.getMessageResponseDetail().isEmpty()){

                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,ex.getMessage());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }else{
                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,detail.getMessageResponseDetail());
                    detail.setResponseDetail(List.of(responseDetailDTO));
                }
            });
        }

        return new MainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseDetailListDTOS);
    }


    @ExceptionHandler(NotAcceptableException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public final MainResponseDTO<Object> handleNotAcceptableException(NotAcceptableException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        List<ResultResponseDTO> responseDetailListDTOS = ex.getData();
        if(!Objects.isNull(responseDetailListDTOS)){
            responseDetailListDTOS.stream().forEach((detail)->{
                detail.setStatusDetail(ResponseStatusEnum.FAILED.responseMessage);
                if(Objects.isNull(detail.getMessageResponseDetail()) || detail.getMessageResponseDetail().isEmpty()){

                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,ex.getMessage());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }else{
                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,detail.getMessageResponseDetail());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }
            });
        }
        return new MainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseDetailListDTOS);
    }


    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public final MainResponseDTO<Object> handleInternalServerErrorException(InternalServerErrorException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        List<ResultResponseDTO> responseDetailListDTOS = ex.getData();
        if(!Objects.isNull(responseDetailListDTOS)){
            responseDetailListDTOS.stream().forEach((detail)->{
                detail.setStatusDetail(ResponseStatusEnum.FAILED.responseMessage);
                if(Objects.isNull(detail.getMessageResponseDetail()) || detail.getMessageResponseDetail().isEmpty()){

                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,ex.getMessage());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }else{
                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,detail.getMessageResponseDetail());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }
            });
        }
        return new MainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseDetailListDTOS);
    }



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public final MainResponseDTO<Object> handleNotFoundException(NotFoundException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        List<ResultResponseDTO> responseDetailListDTOS = ex.getData();
        if(!Objects.isNull(responseDetailListDTOS)){

            responseDetailListDTOS.stream().forEach((detail)->{
                detail.setStatusDetail(ResponseStatusEnum.FAILED.responseMessage);
                if(Objects.isNull(detail.getMessageResponseDetail()) || detail.getMessageResponseDetail().isEmpty()){

                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,ex.getMessage());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }else{
                    ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,detail.getMessageResponseDetail());
                    detail.setResponseDetail(Arrays.asList(responseDetailDTO));
                }
            });
        }
        responseDetailListDTOS.stream().forEach((detail)->{
            detail.setStatusDetail(ResponseStatusEnum.FAILED.responseMessage);
            if(Objects.isNull(detail.getMessageResponseDetail()) || detail.getMessageResponseDetail().isEmpty()){

                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,ex.getMessage());
                detail.setResponseDetail(Arrays.asList(responseDetailDTO));
            }else{
                ResponseDetailDTO responseDetailDTO = new ResponseDetailDTO(ResponseStatusEnum.FAILED.responseCode,detail.getMessageResponseDetail());
                detail.setResponseDetail(Arrays.asList(responseDetailDTO));
            }
        });
        return new MainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseDetailListDTOS);

    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final RequestResponseDTO<String> handleBindException(WebExchangeBindException ex){
        FieldError fieldError = ex.getBindingResult().getFieldError();

        String field = "";
        String message = "";
        if(!Objects.isNull(fieldError)){
            field = fieldError.getField();
            message = fieldError.getDefaultMessage();
        }
        return new RequestResponseDTO<>(
                message +" : "+field, null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RequestResponseDTO<Map<String,String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract constraint violations
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }

        return new RequestResponseDTO<>(
                "Bad Request", errors);
    }

}
