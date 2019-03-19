package com.sh.crm.ws.proxy;

import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.ws.Holder;
import java.util.UUID;


public abstract class BasicWebServiceProxy<T> {
    protected String wsdl;
    @Value("${mw.user}")
    protected String mwuser;
    @Value("${mw.channel}")
    protected String channel;

    public abstract T getProxyService() throws Exception;

    public RequestHeader getRequestHeader(String customerBasic, String nationalID, String lang) {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setChannelID( this.channel );
        requestHeader.setCustomerNo( customerBasic );
        requestHeader.setRequestID( UUID.randomUUID().toString() );
        if (lang == null || lang.isEmpty())
            lang = "en";
        requestHeader.setLanguage( lang );
        requestHeader.setUserID( this.mwuser );
        requestHeader.setTokenKey( "" );
        if (nationalID == null || nationalID.isEmpty())
            nationalID = "";
        requestHeader.setIdNumber( nationalID );
        requestHeader.setCuid( "" );
        return requestHeader;
    }

    public String getWsdl() {
        return wsdl;
    }

    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
    }

    public int handleResponse(Holder<ResponseHeader> responseHeader) {

        if (responseHeader != null && responseHeader.value != null) {
            if (responseHeader.value.getStatus().intValue() == 0)
                return 0;
        }
        return -1;
    }

}
