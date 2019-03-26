package com.sh.crm.rest.ws;

import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

public abstract class GeneralWSRest {
    public ResponseEntity<?> handleResponse(WSResponseHolder<?> wsResponseHolder) {

        if (wsResponseHolder != null && wsResponseHolder.getStatus() == 0)
            return ResponseEntity.ok(wsResponseHolder.getValue());
        else {
            if (wsResponseHolder.getResponseHeader() == null) {
                ResponseHeader responseHeader = new ResponseHeader();
                responseHeader.setState( ResponseState.FAILURE );
                responseHeader.setStatus( new BigInteger( "-1" ) );
                responseHeader.setMessageID( "UNKNOW ERROR" );
                wsResponseHolder.setResponseHeader( responseHeader );
            }
            return ResponseEntity.badRequest().body( wsResponseHolder.getResponseHeader() );
        }
    }
}
