package com.sh.crm.rest.ws.creditCardsWebServices;


import com.sh.crm.general.holders.ws.CreditCardTransactionsRequest;
import com.sh.crm.rest.ws.GeneralWSRest;
import com.sh.crm.ws.impl.creditcards.CreditCardsGeneralWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.accountService.GetAccountsListResponse;
import com.sh.ws.creditcards.creditcardgeneralservices.GetCreditCardListResponse;
import com.sh.ws.creditcards.creditcardgeneralservices.GetCreditCardTransactionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ws/creditcards", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditCardsWebServicesRest extends GeneralWSRest {

    @Autowired
    private CreditCardsGeneralWebServicesImpl creditCardsGeneralWebServices;

    @GetMapping("cardsList/{customerBasic}/{status}/{idnumber}/{lang}")
    ResponseEntity<?> getCreditCardsList(@PathVariable("customerBasic") String customerBasic,
                                         @PathVariable("lang") String lang,
                                         @PathVariable("idnumber") String idnumber,
                                         @PathVariable("status") String status) {
        if (status == null || status.equalsIgnoreCase("null") || status.equalsIgnoreCase("ignore"))
            status = null;
        WSResponseHolder<GetCreditCardListResponse> wsResponseHolder = creditCardsGeneralWebServices.getCreditCardsList(customerBasic, status, idnumber, lang);
        return handleResponse(wsResponseHolder);
    }

    @PostMapping("cardTransactions")
    ResponseEntity<?> getCreditCardsList(@Valid @RequestBody CreditCardTransactionsRequest request) {
        WSResponseHolder<GetCreditCardTransactionsResponse> wsResponseHolder = creditCardsGeneralWebServices.getCreditCardsTrxs(request);
        return handleResponse(wsResponseHolder);
    }
}
