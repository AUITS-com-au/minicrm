package com.sh.crm.jpa.entities;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "ServiceAuditLog")
@EntityListeners(AuditingEntityListener.class)
public class ServiceAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @CreatedDate
    @Column(name = "DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @CreatedBy
    @Size(min = 1, max = 50)
    @Column(name = "UserID")
    private String userID;
    @Size(max = 50)
    @Column(name = "ServiceName")
    private String serviceName;
    @Size(max = 10)
    @Column(name = "CustomerID")
    private String customerID;
    @Size(max = 20)
    @Column(name = "CreditCardNo")
    private String creditCardNo;
    @Size(max = 20)
    @Column(name = "AccountNo")
    private String accountNo;
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
    @Size(max = 50)
    @Column(name = "IPAddress")
    private String iPAddress;
    @Column(name = "StatusCode")
    private String statusCode;

    public ServiceAuditLog() {
    }

    public ServiceAuditLog(Long id) {
        this.id = id;
    }

    public ServiceAuditLog(Long id, Date dateTime, String userID) {
        this.id = id;
        this.dateTime = dateTime;
        this.userID = userID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getIPAddress() {
        return iPAddress;
    }

    public void setIPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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
        if (!(object instanceof ServiceAuditLog)) {
            return false;
        }
        ServiceAuditLog other = (ServiceAuditLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

}
