package com.sh.crm.general.holders.ws;

import com.sh.ws.userServices.retailservices.baj.com.AccountNo;

public class AccountTransactionsRequest {
    private AccountNo accountNo;
    private String customerBasic;
    private String segment;
    private String lang;
    private String idnumber;
    private long fromDate;
    private long toDate;

    public AccountTransactionsRequest() {

    }

    public AccountNo getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(AccountNo accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerBasic() {
        return customerBasic;
    }

    public void setCustomerBasic(String customerBasic) {
        this.customerBasic = customerBasic;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
