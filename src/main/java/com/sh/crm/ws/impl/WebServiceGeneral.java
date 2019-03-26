package com.sh.crm.ws.impl;

import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;

import java.math.BigInteger;

public abstract class WebServiceGeneral {
    protected WSResponseHolder<?> wsResponseHolder;

    public void generateFailureResponse() {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setState(ResponseState.FAILURE);
        responseHeader.setStatus(new BigInteger("-1"));
        responseHeader.setMessageID("UNKNOW ERROR");
        wsResponseHolder = new WSResponseHolder<>(-1, null, responseHeader);
    }
}
