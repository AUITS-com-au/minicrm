package com.sh.crm.rest.ws.accountWebServices;


import com.sh.crm.general.holders.ws.AccountTransactionsRequest;
import com.sh.crm.general.holders.ws.enums.MWServiceName;
import com.sh.crm.jpa.entities.ServiceAuditLog;
import com.sh.crm.rest.ws.GeneralWSRest;
import com.sh.crm.ws.impl.account.AccountWebServicesImpl;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.accountService.GetAccountsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
                                          @PathVariable("segment") String segment,
                                          HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, customerBasic, MWServiceName.ACCOUNTS_LIST );
        WSResponseHolder<GetAccountsListResponse> wsResponseHolder = accountWebServices.getAccountList( customerBasic, idnumber, lang );
        updateSuccessAuditLog( serviceAuditLog );
        if (maskedSegments != null && maskedSegments.contains( segment )) {
            return handleResponse( wsResponseHolder, true );
        } else
            return handleResponse( wsResponseHolder );
    }

    @PostMapping("accountTransactions")
    ResponseEntity<?> getAccountTransactions(@RequestBody AccountTransactionsRequest request,
                                             HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, request.getCustomerBasic(), MWServiceName.ACCOUNTS_LIST );
        serviceAuditLog.setAccountNo( request.getAccountNo().toString() );
        WSResponseHolder wsResponseHolder = accountWebServices.getAccountTransaction( request.getCustomerBasic(), request.getIdnumber(), request.getLang(), request.getAccountNo(), request.getFromDate(), request.getToDate() );
        updateSuccessAuditLog( serviceAuditLog );
        if (maskedSegments != null && maskedSegments.contains( request.getSegment() )) {
            return handleResponse( wsResponseHolder, true );
        } else
            return handleResponse( wsResponseHolder );
    }

    @PostMapping("sendAccountStatement")
    ResponseEntity<?> sendAccountStmt(@RequestBody AccountTransactionsRequest request,
                                      HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, request.getCustomerBasic(), MWServiceName.ACCOUNT_STMT );
        serviceAuditLog.setAccountNo( request.getAccountNo().toString() );

        serviceAuditLog.setExtField1( String.valueOf( request.getStatementType() ) );
        serviceAuditLog.setExtField2( request.getEmail() );
        serviceAuditLog.setExtField3( getFormattedDateFromLong( request.getFromDate() ) );
        serviceAuditLog.setExtField4( getFormattedDateFromLong( request.getToDate() ) );
        WSResponseHolder wsResponseHolder = accountWebServices.sendAccountStatement( request.getCustomerBasic(), request.getIdnumber(),
                request.getLang(), request.getAccountNo(), request.getStatementType(), request.getFromDate(),
                request.getToDate(), request.getEmail() );
        updateSuccessAuditLog( serviceAuditLog );
        return handleResponse( wsResponseHolder );
    }

    @PostMapping("sendIBANSMS")
    ResponseEntity<?> sendIBANSMS(@RequestBody AccountTransactionsRequest request,
                                  HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, request.getCustomerBasic(), MWServiceName.ACCOUNT_IBAN_SMS );
        serviceAuditLog.setAccountNo( request.getAccountNo().toString() );
        WSResponseHolder wsResponseHolder = accountWebServices.sendAccountIBANSMS( request.getCustomerBasic(), request.getIdnumber(), request.getLang(), request.getAccountNo() );
        updateSuccessAuditLog( serviceAuditLog );
        return handleResponse( wsResponseHolder );
    }

    @PostMapping("chequeBookStatus")
    ResponseEntity<?> chequeBookStatus(@RequestBody AccountTransactionsRequest request,
                                       HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest, request.getCustomerBasic(), MWServiceName.ACCOUNT_CHEQUE_BOOK_STATUS );
        serviceAuditLog.setAccountNo( request.getAccountNo().toString() );
        WSResponseHolder wsResponseHolder = accountWebServices.chequeBookStatus( request.getCustomerBasic(), request.getIdnumber(), request.getLang(), request.getAccountNo() );
        updateSuccessAuditLog( serviceAuditLog );
        return handleResponse( wsResponseHolder );
    }

    @PostMapping("chequeBookRequest")
    ResponseEntity<?> chequeBookRequest(@RequestBody AccountTransactionsRequest request, HttpServletRequest httpServletRequest) {
        ServiceAuditLog serviceAuditLog = getAudits( httpServletRequest,
                request.getCustomerBasic(), MWServiceName.ACCOUNT_CHEQUE_BOOK_REQUEST );
        serviceAuditLog.setAccountNo( request.getAccountNo().toString() );
        WSResponseHolder wsResponseHolder = accountWebServices.chequeBookRequest( request.getCustomerBasic(), request.getIdnumber(), request.getLang(), request.getAccountNo() );
        updateSuccessAuditLog( serviceAuditLog );
        return handleResponse( wsResponseHolder );
    }
}
