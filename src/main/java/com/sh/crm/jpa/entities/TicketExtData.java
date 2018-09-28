/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "ticketExtData")
@XmlRootElement
public class TicketExtData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @JsonIgnore

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticketID", nullable = false, insertable = true)
    private Ticket ticketID;
    @Size(max = 500)
    @Column(name = "BranchName")
    private String branchName;
    @Size(max = 50)
    @Column(name = "TransactionCode")
    private String transactionCode;
    @Size(max = 2147483647)
    @Column(name = "TransactionDesc")
    private String transactionDesc;
    @Size(max = 50)
    @Column(name = "TransactionType")
    private String transactionType;
    @Size(max = 50)
    @Column(name = "CustomerAccount")
    private String customerAccount;
    @Size(max = 20)
    @Column(name = "Amount")
    private String amount;
    @Column(name = "TraxDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date traxDateTime;
    @Column(name = "ExtDate1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date extDate1;
    @Column(name = "ExtDate2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date extDate2;
    @Size(max = 250)
    @Column(name = "ExtField1")
    private String extField1;
    @Size(max = 250)
    @Column(name = "ExtField2")
    private String extField2;
    @Size(max = 250)
    @Column(name = "ExtField3")
    private String extField3;
    @Size(max = 250)
    @Column(name = "ExtField4")
    private String extField4;
    @Size(max = 250)
    @Column(name = "ExtField5")
    private String extField5;
    @Size(max = 250)
    @Column(name = "ExtField6")
    private String extField6;
    @Size(max = 250)
    @Column(name = "ExtField7")
    private String extField7;
    @Size(max = 250)
    @Column(name = "ExtField8")
    private String extField8;
    @Size(max = 250)
    @Column(name = "ExtField9")
    private String extField9;
    @Size(max = 250)
    @Column(name = "ExtField10")
    private String extField10;
    @Size(max = 250)
    @Column(name = "ExtField11")
    private String extField11;
    @Size(max = 250)
    @Column(name = "ExtField12")
    private String extField12;
    @Size(max = 250)
    @Column(name = "ExtField13")
    private String extField13;
    @Size(max = 250)
    @Column(name = "ExtField14")
    private String extField14;
    @Size(max = 250)
    @Column(name = "ExtField15")
    private String extField15;
    @Size(max = 250)
    @Column(name = "ExtField16")
    private String extField16;
    @Size(max = 250)
    @Column(name = "ExtField17")
    private String extField17;
    @Size(max = 250)
    @Column(name = "ExtField18")
    private String extField18;
    @Size(max = 250)
    @Column(name = "ExtField19")
    private String extField19;
    @Size(max = 250)
    @Column(name = "ExtField20")
    private String extField20;

    public TicketExtData() {
    }

    public TicketExtData(Long id) {
        this.id = id;
    }

    public TicketExtData(Long id, Ticket ticketID) {
        this.id = id;
        this.ticketID = ticketID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicketID() {
        return ticketID;
    }

    public void setTicketID(Ticket ticketID) {
        this.ticketID = ticketID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getTraxDateTime() {
        return traxDateTime;
    }

    public void setTraxDateTime(Date traxDateTime) {
        this.traxDateTime = traxDateTime;
    }

    public Date getExtDate1() {
        return extDate1;
    }

    public void setExtDate1(Date extDate1) {
        this.extDate1 = extDate1;
    }

    public Date getExtDate2() {
        return extDate2;
    }

    public void setExtDate2(Date extDate2) {
        this.extDate2 = extDate2;
    }

    public String getExtField1() {
        return extField1;
    }

    public void setExtField1(String extField1) {
        this.extField1 = extField1;
    }

    public String getExtField2() {
        return extField2;
    }

    public void setExtField2(String extField2) {
        this.extField2 = extField2;
    }

    public String getExtField3() {
        return extField3;
    }

    public void setExtField3(String extField3) {
        this.extField3 = extField3;
    }

    public String getExtField4() {
        return extField4;
    }

    public void setExtField4(String extField4) {
        this.extField4 = extField4;
    }

    public String getExtField5() {
        return extField5;
    }

    public void setExtField5(String extField5) {
        this.extField5 = extField5;
    }

    public String getExtField6() {
        return extField6;
    }

    public void setExtField6(String extField6) {
        this.extField6 = extField6;
    }

    public String getExtField7() {
        return extField7;
    }

    public void setExtField7(String extField7) {
        this.extField7 = extField7;
    }

    public String getExtField8() {
        return extField8;
    }

    public void setExtField8(String extField8) {
        this.extField8 = extField8;
    }

    public String getExtField9() {
        return extField9;
    }

    public void setExtField9(String extField9) {
        this.extField9 = extField9;
    }

    public String getExtField10() {
        return extField10;
    }

    public void setExtField10(String extField10) {
        this.extField10 = extField10;
    }

    public String getExtField11() {
        return extField11;
    }

    public void setExtField11(String extField11) {
        this.extField11 = extField11;
    }

    public String getExtField12() {
        return extField12;
    }

    public void setExtField12(String extField12) {
        this.extField12 = extField12;
    }

    public String getExtField13() {
        return extField13;
    }

    public void setExtField13(String extField13) {
        this.extField13 = extField13;
    }

    public String getExtField14() {
        return extField14;
    }

    public void setExtField14(String extField14) {
        this.extField14 = extField14;
    }

    public String getExtField15() {
        return extField15;
    }

    public void setExtField15(String extField15) {
        this.extField15 = extField15;
    }

    public String getExtField16() {
        return extField16;
    }

    public void setExtField16(String extField16) {
        this.extField16 = extField16;
    }

    public String getExtField17() {
        return extField17;
    }

    public void setExtField17(String extField17) {
        this.extField17 = extField17;
    }

    public String getExtField18() {
        return extField18;
    }

    public void setExtField18(String extField18) {
        this.extField18 = extField18;
    }

    public String getExtField19() {
        return extField19;
    }

    public void setExtField19(String extField19) {
        this.extField19 = extField19;
    }

    public String getExtField20() {
        return extField20;
    }

    public void setExtField20(String extField20) {
        this.extField20 = extField20;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketExtData)) {
            return false;
        }
        TicketExtData other = (TicketExtData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.TicketExtData[ id=" + id + " ]";
    }

}
