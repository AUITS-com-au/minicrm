package com.sh.crm.ws.proxy.users;

import com.sh.crm.ws.proxy.BasicWebServiceProxy;
import com.sh.ws.userServices.userservices.UserServices;
import com.sh.ws.userServices.userservices.UserServicesHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;


@Service
public class UsersServicesProxy extends BasicWebServiceProxy<UserServices> {

    @Value("${mw.userservices.wsdl}")
    private String wsdl;
    private UserServicesHttpService proxyService;

    @Override
    public UserServices getProxyService() throws Exception {
        if (proxyService == null)
            proxyService = new UserServicesHttpService( new URL( wsdl ) );
        return proxyService.getUserServicesHttpPort();
    }

}
