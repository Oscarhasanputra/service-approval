package com.bit.microservices.service_approval.exceptions.views;

import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import com.bit.microservices.service_approval.exceptions.BaseResponseCodeException;
import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class NotFoundViewException extends BaseResponseCodeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -5319059487974095384L;
    private final String message;

    private final Object data;


    public NotFoundViewException(String message, Object data) {
        super(HttpStatus.NOT_FOUND,message);
        this.message = message;
        this.data = data;
    }
    public NotFoundViewException(String message, Object data, String responseCode, MessageCodeEnum messageCodeEnum) {
        super(HttpStatus.NOT_FOUND,message,responseCode,messageCodeEnum);
        this.message = message;
        this.data = data;
    }

    public Object getData(){
        return this.data;
    }
}
