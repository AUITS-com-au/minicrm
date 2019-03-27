package com.sh.crm.ws.proxy.creditcards;

import com.sh.crm.ws.proxy.BasicWebServiceProxy;
import com.sh.ws.creditcards.creditcardgeneralservices.CreditCardGeneralServices;
import com.sh.ws.creditcards.creditcardgeneralservices.CreditCardGeneralServicesHttpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URL;

@Service
public class CreditCardsGeneralServicesProxy extends BasicWebServiceProxy<CreditCardGeneralServices> {
    @Value("${mw.creditcards.general.services.wsdl}")
    private String wsdl;
    private CreditCardGeneralServicesHttpService proxyService;

    @Override
    public CreditCardGeneralServices getProxyService() throws Exception {
        if (proxyService == null)
            proxyService = new CreditCardGeneralServicesHttpService(new URL(wsdl));
        return proxyService.getCreditCardGeneralServicesHttpPort();
    }
}
