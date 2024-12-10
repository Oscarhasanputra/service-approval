package com.bit.microservices.service_approval.exceptions.handler;

import com.bit.microservices.service_approval.enums.ResponseStatusEnum;
import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import com.bit.microservices.service_approval.exceptions.BadRequestException;
import com.bit.microservices.service_approval.exceptions.ExceptionPrinter;
import com.bit.microservices.service_approval.exceptions.views.BadRequestViewException;
import com.bit.microservices.service_approval.exceptions.views.InternalServerErrorViewException;
import com.bit.microservices.service_approval.exceptions.views.NotFoundViewException;
import com.bit.microservices.service_approval.model.response.MainResponseDTO;
import com.bit.microservices.service_approval.model.response.ResponseDetailDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import com.bit.microservices.service_approval.model.response.view.ViewMainResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ViewExceptionHandler {

    @ExceptionHandler(BadRequestViewException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final ViewMainResponseDTO<Object> handleBadRequestViewException(BadRequestViewException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        Object dataResponse = ex.getData();
        MessageCodeEnum messageCodeEnum = Objects.isNull(ex.getMessageCodeEnum())?MessageCodeEnum.CODE_NOT_EXIST:ex.getMessageCodeEnum();
        BigDecimal responseCode = new BigDecimal(ex.getStatusCode().value()+ex.getResponseCode()+messageCodeEnum.code);

        return new ViewMainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseCode,ex.getMessage(),dataResponse);
    }

    @ExceptionHandler(NotFoundViewException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public final ViewMainResponseDTO<Object> handleNotFoundViewException(NotFoundViewException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        Object responseData = ex.getData();
        MessageCodeEnum messageCodeEnum = Objects.isNull(ex.getMessageCodeEnum())?MessageCodeEnum.CODE_NOT_EXIST:ex.getMessageCodeEnum();
        BigDecimal responseCode = new BigDecimal(ex.getStatusCode().value()+ex.getResponseCode()+messageCodeEnum.code);
        return new ViewMainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseCode,ex.getMessage(),responseData);
    }

    @ExceptionHandler(InternalServerErrorViewException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public final ViewMainResponseDTO<Object> handleInternalServerErrorViewException(InternalServerErrorViewException ex){

        ExceptionPrinter print = new ExceptionPrinter(ex);
        log.error(print.getMessage());
        Object dataResponse = ex.getData();
        MessageCodeEnum messageCodeEnum = Objects.isNull(ex.getMessageCodeEnum())?MessageCodeEnum.CODE_NOT_EXIST:ex.getMessageCodeEnum();
        BigDecimal responseCode = new BigDecimal(ex.getStatusCode().value()+ex.getResponseCode()+messageCodeEnum.code);
        return new ViewMainResponseDTO<>(ResponseStatusEnum.FAILED.responseMessage, ResponseStatusEnum.FAILED.code,responseCode,ex.getMessage(),dataResponse);
    }

}
