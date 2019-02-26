package com.sh.crm.general.holders;

public class SearchTicketsCustomerContainer {
    private String customerBasic;
    private String customerMobile;
    private String customerName;
    private String customerSegment;
    private String customerBranch;
    private String customerEmail;
    private String nan;

    public SearchTicketsCustomerContainer() {

    }

    public String getCustomerBasic() {
        return customerBasic;
    }

    public void setCustomerBasic(String customerBasic) {
        this.customerBasic = customerBasic;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSegment() {
        return customerSegment;
    }

    public void setCustomerSegment(String customerSegment) {
        this.customerSegment = customerSegment;
    }

    public String getCustomerBranch() {
        return customerBranch;
    }

    public void setCustomerBranch(String customerBranch) {
        this.customerBranch = customerBranch;
    }

    public String getNan() {
        return nan;
    }

    public void setNan(String nan) {
        this.nan = nan;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "SearchTicketsCustomerContainer{" +
                "customerBasic='" + customerBasic + '\'' +
                ", customerMobile='" + customerMobile + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerSegment='" + customerSegment + '\'' +
                ", customerBranch='" + customerBranch + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", nan='" + nan + '\'' +
                '}';
    }
}
