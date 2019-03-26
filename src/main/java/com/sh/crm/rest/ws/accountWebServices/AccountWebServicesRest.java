package com.sh.crm.rest.ws.accountWebServices;


import com.sh.crm.rest.ws.GeneralWSRest;
import com.sh.crm.ws.impl.account.AccountWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.accountService.GetAccountsListResponse;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/ws/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountWebServicesRest extends GeneralWSRest {
    @Autowired
    private AccountWebServicesImpl accountWebServices;

    @GetMapping("accountList/{customerBasic}/{idnumber}/{lang}")
    ResponseEntity<?> getCustomerAccounts(@PathVariable("customerBasic") String customerBasic, @PathVariable("lang") String lang, @PathVariable("idnumber") String idnumber) {
        WSResponseHolder<GetAccountsListResponse> wsResponseHolder = accountWebServices.getAccountList(customerBasic, idnumber, lang);
        return handleResponse(wsResponseHolder);
    }
}
