package com.bit.microservices.service_approval.exceptions;

import com.bit.microservices.service_approval.model.response.ResultListDTO;
import com.bit.microservices.service_approval.model.response.ResultResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BadRequestException extends ResponseStatusException implements Serializable {
    @Serial
    private static final long serialVersionUID = 8694798166778863854L;
    private final String message;

    private final List<ResultResponseDTO> data;
    public BadRequestException(String message, ResultListDTO<ResultResponseDTO> data) {
        super(HttpStatus.BAD_REQUEST,message);
        this.message = message;
        this.data = data;
    }

    public List<ResultResponseDTO> getData(){
        return this.data;
    }
}
