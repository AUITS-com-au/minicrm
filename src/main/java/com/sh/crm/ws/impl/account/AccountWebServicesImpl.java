package com.sh.crm.ws.impl.account;

import com.sh.crm.ws.impl.WebServiceGeneral;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.account.AccountServicesProxy;
import com.sh.ws.accountService.GetAccountsListRequest;
import com.sh.ws.accountService.GetAccountsListResponse;
import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.userServices.userservices.GetCustomerProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;

@Service
public class AccountWebServicesImpl extends WebServiceGeneral {

    @Autowired
    private AccountServicesProxy accountServicesProxy;

    public WSResponseHolder getAccountList(String customerBasic, String lang, String idnumber) {

        GetAccountsListRequest accountsListRequest = new GetAccountsListRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        accountsListRequest.setCustomerNo(customerBasic);
        Holder<GetAccountsListResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();

        try {
            accountServicesProxy.getProxyService().getAccountsList(accountsListRequest, requestHeader, responseHolder, responseHeaderHolder);
            int status = accountServicesProxy.handleResponse(responseHeaderHolder);
            if (status == 0) {
                wsResponseHolder = new WSResponseHolder<GetAccountsListResponse>(status, responseHolder.value);
            } else {
                wsResponseHolder = new WSResponseHolder<>(status, null);
                wsResponseHolder.setResponseHeader(responseHeaderHolder.value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            generateFailureResponse();
        }

        return wsResponseHolder;
    }
}
