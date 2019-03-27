package com.sh.crm.rest.ws.accountWebServices;


import com.sh.crm.general.holders.ws.AccountTransactionsRequest;
import com.sh.crm.rest.ws.GeneralWSRest;
import com.sh.crm.ws.impl.account.AccountWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.accountService.GetAccountsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ws/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountWebServicesRest extends GeneralWSRest {
    @Autowired
    private AccountWebServicesImpl accountWebServices;

    @Value("${mw.masked.segments}")
    private List<String> maskedSegments;

    @GetMapping("accountList/{customerBasic}/{segment}/{idnumber}/{lang}")
    ResponseEntity<?> getCustomerAccounts(@PathVariable("customerBasic") String customerBasic,
                                          @PathVariable("lang") String lang,
                                          @PathVariable("idnumber") String idnumber,
                                          @PathVariable("segment") String segment) {
        WSResponseHolder<GetAccountsListResponse> wsResponseHolder = accountWebServices.getAccountList(customerBasic, idnumber, lang);

        if (maskedSegments != null && maskedSegments.contains(segment)) {
            return handleResponse(wsResponseHolder, true);
        } else
            return handleResponse(wsResponseHolder);
    }

    @PostMapping("accountTransactions")
    ResponseEntity<?> getAccountTransactions(@RequestBody AccountTransactionsRequest request) {
        WSResponseHolder wsResponseHolder = accountWebServices.getAccountTransaction(request.getCustomerBasic(), request.getIdnumber(), request.getLang(), request.getAccountNo(), request.getFromDate(), request.getToDate());
        if (maskedSegments != null && maskedSegments.contains(request.getSegment())) {
            return handleResponse(wsResponseHolder, true);
        } else
            return handleResponse(wsResponseHolder);

    }
}
