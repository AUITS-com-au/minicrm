package com.sh.crm.ws.impl.account;

import com.sh.crm.ws.impl.WebServiceGeneral;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.account.AccountServicesProxy;
import com.sh.ws.accountService.*;
import com.sh.ws.request.RequestHeader;
import com.sh.ws.response.ResponseHeader;
import com.sh.ws.userServices.retailservices.baj.com.Account;
import com.sh.ws.userServices.retailservices.baj.com.AccountNo;
import com.sh.ws.userServices.retailservices.baj.com.PagingInfo;
import com.sh.ws.userServices.retailservices.baj.com.SortOrder;
import com.sh.ws.userServices.userservices.GetCustomerProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class AccountWebServicesImpl extends WebServiceGeneral {

    @Autowired
    private AccountServicesProxy accountServicesProxy;

    public WSResponseHolder getAccountList(String customerBasic, String lang, String idnumber) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetAccountsListRequest accountsListRequest = new GetAccountsListRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        accountsListRequest.setCustomerNo(customerBasic);
        Holder<GetAccountsListResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();

        try {
            accountServicesProxy.getProxyService().getAccountsList(accountsListRequest, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);
        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }

        return wsResponseHolder;
    }

    public WSResponseHolder getAccountTransaction(String customerBasic, String idnumber, String lang, AccountNo account, long fromDate, long
            toDate) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetAccountTransactionListRequest request = new GetAccountTransactionListRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        request.setCustomerNo(customerBasic);
        request.setAccountNo(account);
        request.setPagingInfo(getDefaultPagingInfo());
        Holder<GetAccountTransactionListResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {

            request.setFromDate(getXmlGregorianCalendar(fromDate));
            request.setToDate(getXmlGregorianCalendar(toDate));
            request.setAmountFrom(BigDecimal.ONE);

            accountServicesProxy.getProxyService().getAccountTransactionList(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder sendAccountStatement(String customerBasic, String idnumber, String lang, AccountNo account, int sendType, long fromDate, long
            toDate, String email) {
        WSResponseHolder<?> wsResponseHolder = null;
        SendAccountStatementRequest request = new SendAccountStatementRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        request.setCustomerNo(customerBasic);
        request.setAccountNo(account);
        if (sendType == 1) {
            //email
            request.setEmail(email);
            request.setSendEmail(true);
        } else {
            request.setSendMail(true);
        }

        Holder<SendAccountStatementResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            request.setFromDate(getXmlGregorianCalendar(fromDate));
            request.setToDate(getXmlGregorianCalendar(toDate));
            accountServicesProxy.getProxyService().sendAccountStatement(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder sendAccountIBANSMS(String customerBasic, String idnumber, String lang, AccountNo account) {
        WSResponseHolder<?> wsResponseHolder = null;
        SendIBANSMSRequest request = new SendIBANSMSRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        request.setCustomerNo(customerBasic);
        request.setAccountNo(account);
        Holder<SendIBANSMSResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            accountServicesProxy.getProxyService().sendIBANSMS(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder chequeBookStatus(String customerBasic, String idnumber, String lang, AccountNo account) {
        WSResponseHolder<?> wsResponseHolder = null;
        GetChequeBookStatusRequest request = new GetChequeBookStatusRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        request.setAccountNo(account);
        Holder<GetChequeBookStatusResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            accountServicesProxy.getProxyService().getChequeBookStatus(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

    public WSResponseHolder chequeBookRequest(String customerBasic, String idnumber, String lang, AccountNo account) {
        WSResponseHolder<?> wsResponseHolder = null;
        ChequeBookRequestRequest request = new ChequeBookRequestRequest();
        RequestHeader requestHeader = accountServicesProxy.getRequestHeader(customerBasic, idnumber, lang);
        request.setAccountNumber(account);
        request.setBranchCode(account.getBranch());
        Holder<ChequeBookRequestResponse> responseHolder = new Holder<>();
        Holder<ResponseHeader> responseHeaderHolder = new Holder<>();
        try {
            accountServicesProxy.getProxyService().chequeBookRequest(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }
}
