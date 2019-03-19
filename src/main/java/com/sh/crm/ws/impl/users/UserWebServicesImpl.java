package com.sh.crm.ws.impl.users;

import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.users.UsersServicesProxy;
import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import com.sh.ws.userServices.userservices.GetCustomerProfileRequest;
import com.sh.ws.userServices.userservices.GetCustomerProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;
import java.math.BigInteger;

@Service
public class UserWebServicesImpl {
    @Autowired
    private UsersServicesProxy usersServicesProxy;


    public WSResponseHolder getCustomerProfile(String customerBasic, String lang) {
        GetCustomerProfileRequest request = new GetCustomerProfileRequest();
        WSResponseHolder<GetCustomerProfileResponse> wsResponseHolder;
        request.setCustomerNo( customerBasic );
        request.setExtendData( true );
        RequestHeader requestHeader = usersServicesProxy.getRequestHeader( customerBasic, null, lang );
        Holder<GetCustomerProfileResponse> customerProfileResponseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            usersServicesProxy.getProxyService().getCustomerProfile( request, requestHeader, customerProfileResponseHolder, responseHeaderHolder );
            int status = usersServicesProxy.handleResponse( responseHeaderHolder );

            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<>( status, customerProfileResponseHolder.value );
            } else {
                wsResponseHolder = new WSResponseHolder<>( status, null );
                wsResponseHolder.setResponseHeader( responseHeaderHolder.value );
            }
            return wsResponseHolder;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.setState( ResponseState.FAILURE );
            responseHeader.setStatus( new BigInteger( "-1" ) );
            responseHeader.setMessageID( "UNKNOW ERROR" );
            wsResponseHolder = new WSResponseHolder<>( -1, null, responseHeader );
        }

        return wsResponseHolder;

    }
}
