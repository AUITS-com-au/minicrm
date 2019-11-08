package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "MW_Logs")
public class MWLogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Size(max = 10)
    @Column(name = "CustomerNumber")
    private String customerNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ServerIP")
    private String serverIP;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RequestUUID")
    private String requestUUID;
    @Size(max = 50)
    @Column(name = "TokenKey")
    private String tokenKey;
    @Size(max = 50)
    @Column(name = "cuid")
    private String cuid;
    @Size(max = 15)
    @Column(name = "CustomerOfficialID")
    private String customerOfficialID;
    @Size(max = 50)
    @Column(name = "ReqServiceName")
    private String reqServiceName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "FullRequest")
    private String fullRequest;
    @Size(max = 50)
    @Column(name = "ResServiceName")
    private String resServiceName;
    @Basic(optional = false)
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "FullResponse")
    private String fullResponse;
    @Size(max = 250)
    @Column(name = "WsdlFile")
    private String wsdlFile;

    public MWLogs() {
    }

    public MWLogs(Long id) {
        this.id = id;
    }

    public MWLogs(Long id, Date dateTime, String customerNumber, String serverIP, String requestUUID, String fullRequest, String fullResponse) {
        this.id = id;
        this.dateTime = dateTime;
        this.customerNumber = customerNumber;
        this.serverIP = serverIP;
        this.requestUUID = requestUUID;
        this.fullRequest = fullRequest;
        this.fullResponse = fullResponse;
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

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        this.requestUUID = requestUUID;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getCustomerOfficialID() {
        return customerOfficialID;
    }

    public void setCustomerOfficialID(String customerOfficialID) {
        this.customerOfficialID = customerOfficialID;
    }

    public String getReqServiceName() {
        return reqServiceName;
    }

    public void setReqServiceName(String reqServiceName) {
        this.reqServiceName = reqServiceName;
    }

    public String getFullRequest() {
        return fullRequest;
    }

    public void setFullRequest(String fullRequest) {
        this.fullRequest = fullRequest;
    }

    public String getResServiceName() {
        return resServiceName;
    }

    public void setResServiceName(String resServiceName) {
        this.resServiceName = resServiceName;
    }

    public String getFullResponse() {
        return fullResponse;
    }

    public void setFullResponse(String fullResponse) {
        this.fullResponse = fullResponse;
    }

    public String getWsdlFile() {
        return wsdlFile;
    }

    public void setWsdlFile(String wsdlFile) {
        this.wsdlFile = wsdlFile;
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
        if (!(object instanceof MWLogs)) {
            return false;
        }
        MWLogs other = (MWLogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }
}
