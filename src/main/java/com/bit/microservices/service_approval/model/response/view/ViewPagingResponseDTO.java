package com.bit.microservices.service_approval.model.response.view;

import com.bit.microservices.service_approval.utils.pageable.ImplPageData;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serial;
import java.util.List;


@JsonPropertyOrder({"status","code","responseCode","responseMessage","result"})
public class ViewPagingResponseDTO<T> extends ImplPageData<T> {

    @Serial
    private static final long serialVersionUID = -7697972592943940751L;

    public String status;

    public Integer code;

    public String responseCode;

    public String responseMessage;

    public ViewPagingResponseDTO(String status, Integer code, String responseCode, String responseMessage, List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.status=status;
        this.code = code;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ViewPagingResponseDTO(String status, Integer code, String responseCode, String responseMessage, Page<T> page){
        super(page.getContent(),page.getPageable(),page.getTotalElements());
        this.status=status;
        this.code = code;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }



}
