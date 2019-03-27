package com.sh.crm.ws.impl.creditcards;

import com.sh.crm.security.util.Utils;
import com.sh.crm.ws.impl.WebServiceGeneral;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.creditcards.CreditCardsGeneralServicesProxy;
import com.sh.crm.ws.proxy.users.UsersServicesProxy;
import com.sh.ws.creditcards.creditcardgeneralservices.GetCreditCardListRequest;
import com.sh.ws.creditcards.creditcardgeneralservices.GetCreditCardListResponse;
import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.userServices.userservices.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;

@Service
public class CreditCardsGeneralWebServicesImpl extends WebServiceGeneral {
    @Autowired
    private CreditCardsGeneralServicesProxy creditCardsGeneralServicesProxy;

    public WSResponseHolder getCreditCardsList(String customerBasic, String cardStatus, String idNumber, String lang) {
        GetCreditCardListRequest request = new GetCreditCardListRequest();
        Holder<GetCreditCardListResponse> responseHolder = new Holder<>();
        WSResponseHolder<?> wsResponseHolder = null;
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        request.setCustomerNo(customerBasic);
        request.setForceBalanceUpdate(true);
        if (cardStatus != null && !cardStatus.isEmpty())
            request.setCardStatus(cardStatus);

        try {
            RequestHeader requestHeader = creditCardsGeneralServicesProxy.getRequestHeader(customerBasic, idNumber, lang);
            creditCardsGeneralServicesProxy.getProxyService().getCreditCardList(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = creditCardsGeneralServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);
        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }

        return wsResponseHolder;
    }


}
