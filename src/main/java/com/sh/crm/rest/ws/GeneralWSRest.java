package com.sh.crm.rest.ws;

import com.sh.crm.general.holders.ws.enums.MWServiceName;
import com.sh.crm.jpa.entities.ServiceAuditLog;
import com.sh.crm.jpa.repos.ws.ServiceAuditLogRepo;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.accountService.GetAccountTransactionListResponse;
import com.sh.ws.accountService.GetAccountsListResponse;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import com.sh.ws.userServices.retailservices.baj.com.Account;
import com.sh.ws.userServices.retailservices.baj.com.AccountBalance;
import com.sh.ws.userServices.retailservices.baj.com.AccountTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class GeneralWSRest {
    @Autowired
    protected ServiceAuditLogRepo auditLogRepo;
    private static DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd hh:MM:ss a" );

    @Async
    public void updateAuditLog(ServiceAuditLog auditLog) {
        if (auditLogRepo != null)
            auditLogRepo.save( auditLog );
    }

    public void updateSuccessAuditLog(ServiceAuditLog auditLog) {
        auditLog.setStatusCode( "0" );
        updateAuditLog( auditLog );
    }

    public ResponseEntity<?> handleResponse(WSResponseHolder<?> wsResponseHolder) {

        if (wsResponseHolder != null && wsResponseHolder.getStatus() == 0) {

            return ResponseEntity.ok( wsResponseHolder.getValue() );
        } else {
            if (wsResponseHolder.getResponseHeader() == null) {
                ResponseHeader responseHeader = new ResponseHeader();
                responseHeader.setState( ResponseState.FAILURE );
                responseHeader.setStatus( new BigInteger( "-1" ) );
                responseHeader.setMessageID( "UNKNOWN ERROR" );
                wsResponseHolder.setResponseHeader( responseHeader );
            }
            return ResponseEntity.badRequest().body( wsResponseHolder.getResponseHeader() );
        }
    }

    public ResponseEntity<?> handleResponse(WSResponseHolder<?> wsResponseHolder, boolean masked) {
        if (masked && wsResponseHolder.getValue() != null) {
            if (wsResponseHolder.getValue() instanceof GetAccountsListResponse) {
                maskAccountList( wsResponseHolder.getValue() );
            } else if (wsResponseHolder.getValue() instanceof GetAccountTransactionListResponse) {
                maskTraxList( wsResponseHolder.getValue() );
            }
        }
        return handleResponse( wsResponseHolder );
    }

    private void maskAccountList(Object responseObject) {
        GetAccountsListResponse response = (GetAccountsListResponse) responseObject;
        if (response.getAccountList() != null && response.getAccountList().getAccount() != null && !response.getAccountList().getAccount().isEmpty()) {
            for (Account account : response.getAccountList().getAccount()) {
                try {
                    for (AccountBalance balance : account.getAccountBalanceList().getAccountBalance()) {
                        balance.setAmount( "*********" );
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    private void maskTraxList(Object responseObject) {
        GetAccountTransactionListResponse response = (GetAccountTransactionListResponse) responseObject;
        if (response.getAccountTransactionList() != null && response.getAccountTransactionList().getAccountTransaction() != null && !response.getAccountTransactionList().getAccountTransaction().isEmpty()) {
            for (AccountTransaction transaction : response.getAccountTransactionList().getAccountTransaction()) {
                try {
                    transaction.setAmount( "*********" );
                    transaction.setAvailableBalance( "*********" );
                } catch (Exception e) {

                }
            }
        }
    }

    public String getClientIP(HttpServletRequest httpRequest) {
        String xfHeader = httpRequest.getHeader( "X-Forwarded-For" );
        if (xfHeader == null) {
            return httpRequest.getRemoteAddr();
        }
        return xfHeader.split( "," )[0];
    }

    private ServiceAuditLog getServiceAuditLog(HttpServletRequest httpRequest, MWServiceName serviceName, String customerNo) {
        ServiceAuditLog serviceAuditLog = new ServiceAuditLog();
        serviceAuditLog.setIPAddress( getClientIP( httpRequest ) );
        serviceAuditLog.setServiceName( serviceName.toString() );
        serviceAuditLog.setCustomerID( customerNo );
        return serviceAuditLog;
    }

    protected ServiceAuditLog getAudits(HttpServletRequest httpServletRequest, String customerBasic, MWServiceName serviceName) {
        ServiceAuditLog serviceAuditLog = getServiceAuditLog( httpServletRequest, serviceName, customerBasic );
        serviceAuditLog = auditLogRepo.save( serviceAuditLog );
        return serviceAuditLog;
    }

    protected String getFormattedDateFromLong(long dateLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis( dateLong );
        return dateFormat.format( calendar.getTime() );
    }
}
