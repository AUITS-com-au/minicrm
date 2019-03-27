package com.sh.crm.rest.ws;

import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.ws.accountService.GetAccountTransactionListResponse;
import com.sh.ws.accountService.GetAccountsListResponse;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.response.ResponseState;
import com.sh.ws.userServices.retailservices.baj.com.Account;
import com.sh.ws.userServices.retailservices.baj.com.AccountBalance;
import com.sh.ws.userServices.retailservices.baj.com.AccountTransaction;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class GeneralWSRest {
    public ResponseEntity<?> handleResponse(WSResponseHolder<?> wsResponseHolder) {

        if (wsResponseHolder != null && wsResponseHolder.getStatus() == 0) {

            return ResponseEntity.ok(wsResponseHolder.getValue());
        } else {
            if (wsResponseHolder.getResponseHeader() == null) {
                ResponseHeader responseHeader = new ResponseHeader();
                responseHeader.setState(ResponseState.FAILURE);
                responseHeader.setStatus(new BigInteger("-1"));
                responseHeader.setMessageID("UNKNOWN ERROR");
                wsResponseHolder.setResponseHeader(responseHeader);
            }
            return ResponseEntity.badRequest().body(wsResponseHolder.getResponseHeader());
        }
    }

    public ResponseEntity<?> handleResponse(WSResponseHolder<?> wsResponseHolder, boolean masked) {
        if (masked && wsResponseHolder.getValue() != null) {
            if (wsResponseHolder.getValue() instanceof GetAccountsListResponse) {
                maskAccountList(wsResponseHolder.getValue());
            }else if (wsResponseHolder.getValue() instanceof GetAccountTransactionListResponse){
                maskTraxList(wsResponseHolder.getValue());
            }
        }
        return handleResponse(wsResponseHolder);
    }

    private void maskAccountList(Object responseObject) {
        GetAccountsListResponse response = (GetAccountsListResponse) responseObject;
        if (response.getAccountList() != null && response.getAccountList().getAccount() != null && !response.getAccountList().getAccount().isEmpty()) {
            for (Account account : response.getAccountList().getAccount()) {
                try {
                    for (AccountBalance balance : account.getAccountBalanceList().getAccountBalance()) {
                        balance.setAmount("*********");
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
                     transaction.setAmount("*********");
                     transaction.setAvailableBalance("*********");
                } catch (Exception e) {

                }
            }
        }
    }
}
