package com.sh.crm.ws.proxy.account;

import com.sh.crm.ws.proxy.BasicWebServiceProxy;
import com.sh.ws.accountService.AccountServices;
import com.sh.ws.accountService.AccountServicesHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class AccountServicesProxy extends BasicWebServiceProxy<AccountServices> {

    @Value("${mw.accountservices.wsdl}")
    private String wsdl;
    private AccountServicesHttpService proxyService;

    @Override
    public AccountServices getProxyService() throws Exception {
        if (proxyService == null)
            proxyService = new AccountServicesHttpService(new URL(wsdl));
        return proxyService.getAccountServicesHttpPort();
    }

}
