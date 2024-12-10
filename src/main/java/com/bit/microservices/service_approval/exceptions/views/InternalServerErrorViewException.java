package com.bit.microservices.service_approval.exceptions.views;

import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import com.bit.microservices.service_approval.exceptions.BaseResponseCodeException;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class InternalServerErrorViewException extends BaseResponseCodeException implements Serializable {

    private final String message;

    private final Object data;

    public InternalServerErrorViewException(String message, Object data) {
        super(HttpStatus.BAD_REQUEST,message);
        this.message = message;
        this.data = data;
    }
    public InternalServerErrorViewException(String message, Object data, String responseCode, MessageCodeEnum messageCodeEnum) {
        super(HttpStatus.BAD_REQUEST,message,responseCode,messageCodeEnum);
        this.message = message;
        this.data = data;
    }
    public InternalServerErrorViewException(String message, Object data, MessageCodeEnum messageCodeEnum) {
        super(HttpStatus.BAD_REQUEST,message,messageCodeEnum);
        this.message = message;
        this.data = data;
    }

    public Object getData(){
        return this.data;
    }
}
