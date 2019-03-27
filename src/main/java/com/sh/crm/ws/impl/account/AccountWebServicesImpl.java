package com.sh.crm.ws.impl.account;

import com.sh.crm.ws.impl.WebServiceGeneral;
import com.sh.crm.ws.proxy.WSResponseHolder;
import com.sh.crm.ws.proxy.account.AccountServicesProxy;
import com.sh.ws.accountService.GetAccountTransactionListRequest;
import com.sh.ws.accountService.GetAccountTransactionListResponse;
import com.sh.ws.accountService.GetAccountsListRequest;
import com.sh.ws.accountService.GetAccountsListResponse;
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
            final GregorianCalendar fromCal = new GregorianCalendar();
            final GregorianCalendar toCal = new GregorianCalendar();
            fromCal.setTimeInMillis(fromDate);
            toCal.setTimeInMillis(toDate);
            request.setFromDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCal));
             request.setAmountFrom(BigDecimal.ONE);
            request.setToDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(toCal));
            accountServicesProxy.getProxyService().getAccountTransactionList(request, requestHeader, responseHolder, responseHeaderHolder);
            wsResponseHolder = accountServicesProxy.handleResponseBody(responseHeaderHolder, responseHolder);

        } catch (Exception e) {
            e.printStackTrace();
            wsResponseHolder = generateFailureResponse();
        }
        return wsResponseHolder;
    }

}
