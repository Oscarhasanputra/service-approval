package com.bit.microservices.service_approval.exceptions;

import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class BaseResponseCodeException extends ResponseStatusException implements ResponseCodeException {

    protected String responseCode;

    protected MessageCodeEnum messageCodeEnum;
    public BaseResponseCodeException(HttpStatus status,String message,String responseCode,MessageCodeEnum messageCodeEnum) {
        super(status,message);
        this.responseCode =responseCode;
        this.messageCodeEnum=messageCodeEnum;
    }

    public BaseResponseCodeException(HttpStatus status,String message,String responseCode) {
        super(status,message);
        this.responseCode =responseCode;
    }
    public BaseResponseCodeException(HttpStatus status,String message,MessageCodeEnum messageCodeEnum) {
        super(status,message);
        this.messageCodeEnum=messageCodeEnum;
    }
    public BaseResponseCodeException(HttpStatus status,String message) {
        super(status,message);

    }

    @Override
    public String getResponseCode() {
        return this.responseCode;
    }

    @Override
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public MessageCodeEnum getMessageCodeEnum() {
        return this.messageCodeEnum;
    }

    @Override
    public void setMessageCodeEnum(MessageCodeEnum messageCodeEnum) {
        this.messageCodeEnum = messageCodeEnum;
    }
}
