package com.bit.microservices.service_approval.exceptions;

import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.List;

public class NotAcceptableException extends ResponseStatusException implements Serializable {
    private final String message;

    private final List<ResultResponseDTO> data;


    public NotAcceptableException(String message, ResultListDTO<ResultResponseDTO> data) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,message);
        this.message = message;
        this.data= data;
    }


    public List<ResultResponseDTO> getData(){
        return this.data;
    }
}
