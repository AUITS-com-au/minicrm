package com.sh.crm.general.holders.ws;

import com.sh.ws.userServices.retailservices.baj.com.AccountNo;

public class CreditCardTransactionsRequest {
    private String cardRefNo;
    private String customerBasic;
    private String lang;
    private String idnumber;
    private String logo;
    private long fromDate;
    private long toDate;
    private int statementType;
    private String email;

    public CreditCardTransactionsRequest() {

    }

    public int getStatementType() {
        return statementType;
    }

    public void setStatementType(int statementType) {
        this.statementType = statementType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getCustomerBasic() {
        return customerBasic;
    }

    public void setCustomerBasic(String customerBasic) {
        this.customerBasic = customerBasic;
    }

    public String getCardRefNo() {
        return cardRefNo;
    }

    public void setCardRefNo(String cardRefNo) {
        this.cardRefNo = cardRefNo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
