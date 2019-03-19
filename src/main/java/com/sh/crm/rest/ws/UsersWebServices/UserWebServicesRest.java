package com.sh.crm.rest.ws.UsersWebServices;

import com.sh.crm.ws.impl.users.UserWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import com.sh.ws.userServices.userservices.GetCustomerProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/ws/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserWebServicesRest {
    @Autowired
    private UserWebServicesImpl userWebServices;

    @GetMapping("customerProfile/{customerBasic}/{lang}/{token}")
    ResponseEntity<?> getCustomerProfile(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang, @PathVariable("token") String token) {
        WSResponseHolder<GetCustomerProfileResponse> wsResponseHolder = userWebServices.getCustomerProfile( customerBasic, lang );
        if (wsResponseHolder != null && wsResponseHolder.getStatus() == 0)
            return ResponseEntity.ok( wsResponseHolder.getValue() );
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
