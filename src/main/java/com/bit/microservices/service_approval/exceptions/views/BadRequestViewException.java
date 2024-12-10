package com.bit.microservices.service_approval.exceptions.views;

import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import com.bit.microservices.service_approval.exceptions.BaseResponseCodeException;
import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class BadRequestViewException extends BaseResponseCodeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -2731933032635008845L;
    private final String message;

    private final Object data;

    public BadRequestViewException(String message, Object data) {
        super(HttpStatus.BAD_REQUEST,message);
        this.message = message;
        this.data = data;
    }
    public BadRequestViewException(String message, Object data, String responseCode, MessageCodeEnum messageCodeEnum) {
        super(HttpStatus.BAD_REQUEST,message,responseCode,messageCodeEnum);
        this.message = message;
        this.data = data;
    }
    public BadRequestViewException(String message, Object data, MessageCodeEnum messageCodeEnum) {
        super(HttpStatus.BAD_REQUEST,message,messageCodeEnum);
        this.message = message;
        this.data = data;
    }

    public Object getData(){
        return this.data;
    }
}
