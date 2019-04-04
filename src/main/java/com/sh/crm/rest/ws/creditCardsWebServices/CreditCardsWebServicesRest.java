package com.sh.crm.rest.ws.creditCardsWebServices;


import com.sh.crm.general.holders.ws.CreditCardTransactionsRequest;
import com.sh.crm.general.holders.ws.enums.MWServiceName;
import com.sh.crm.jpa.entities.ServiceAuditLog;
import com.sh.crm.rest.ws.GeneralWSRest;
import com.sh.crm.ws.impl.creditcards.CreditCardsGeneralWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.creditcards.creditcardgeneralservices.GetCreditCardListResponse;
import com.sh.ws.creditcards.creditcardgeneralservices.GetCreditCardTransactionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
                                         @PathVariable("status") String status,
                                         HttpServletRequest httpServletRequest) {
        if (status == null || status.equalsIgnoreCase( "null" ) || status.equalsIgnoreCase( "ignore" ))
            status = null;
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, customerBasic, MWServiceName.GET_CREDIT_CARDS_LIST );
        WSResponseHolder<GetCreditCardListResponse> wsResponseHolder = creditCardsGeneralWebServices.getCreditCardsList( customerBasic, status, idnumber, lang );
        updateSuccessAuditLog( serviceAuditLog );
        return handleResponse( wsResponseHolder );
    }

    @PostMapping("cardTransactions")
    ResponseEntity<?> getCardTransactions(@Valid @RequestBody CreditCardTransactionsRequest request,
                                          HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, request.getCustomerBasic(), MWServiceName.GET_ACCOUNT_TRAX );
        serviceAuditLog.setCreditCardNo( request.getCardRefNo() );
        WSResponseHolder<GetCreditCardTransactionsResponse> wsResponseHolder = creditCardsGeneralWebServices.getCreditCardsTrxs( request );
        return handleResponse( wsResponseHolder );
    }
}
