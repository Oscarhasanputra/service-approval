package com.bit.microservices.service_approval.exceptions;

import com.bit.microservices.service_approval.enums.responsecode.MessageCodeEnum;
import org.aspectj.bridge.Message;

public interface ResponseCodeException {
    public String getResponseCode();
    public void setResponseCode(String responseCode);

    public MessageCodeEnum getMessageCodeEnum();
    public void setMessageCodeEnum(MessageCodeEnum messageCodeEnum);
}
