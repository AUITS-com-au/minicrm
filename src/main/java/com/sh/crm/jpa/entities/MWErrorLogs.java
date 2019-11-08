package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "MW_Error_Logs")
public class MWErrorLogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ErrorID")
    private Long errorID;
    @Size(max = 150)
    @Column(name = "ServiceName")
    private String serviceName;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "WSDL")
    private String wsdl;
    @Size(max = 10)
    @Column(name = "BasicNumber")
    private String basicNumber;
    @Size(max = 14)
    @Column(name = "IDNumber")
    private String iDNumber;
    @Size(max = 50)
    @Column(name = "ClientError")
    private String clientError;
    @Size(max = 50)
    @Column(name = "ClientException")
    private String clientException;
    @Size(max = 20)
    @Column(name = "NativeStatus")
    private String nativeStatus;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NativeState")
    private String nativeState;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NativeErrorDec")
    private String nativeErrorDec;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NativeErrorExt")
    private String nativeErrorExt;
    @Size(max = 50)
    @Column(name = "MessageID")
    private String messageID;
    @Size(max = 50)
    @Column(name = "RQUID")
    private String rquid;
    @Column(name = "DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Size(max = 25)
    @Column(name = "Server")
    private String server;

    public MWErrorLogs() {
    }

    public MWErrorLogs(Long errorID) {
        this.errorID = errorID;
    }

    public Long getErrorID() {
        return errorID;
    }

    public void setErrorID(Long errorID) {
        this.errorID = errorID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getWsdl() {
        return wsdl;
    }

    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
    }

    public String getBasicNumber() {
        return basicNumber;
    }

    public void setBasicNumber(String basicNumber) {
        this.basicNumber = basicNumber;
    }

    public String getIDNumber() {
        return iDNumber;
    }

    public void setIDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getClientError() {
        return clientError;
    }

    public void setClientError(String clientError) {
        this.clientError = clientError;
    }

    public String getClientException() {
        return clientException;
    }

    public void setClientException(String clientException) {
        this.clientException = clientException;
    }

    public String getNativeStatus() {
        return nativeStatus;
    }

    public void setNativeStatus(String nativeStatus) {
        this.nativeStatus = nativeStatus;
    }

    public String getNativeState() {
        return nativeState;
    }

    public void setNativeState(String nativeState) {
        this.nativeState = nativeState;
    }

    public String getNativeErrorDec() {
        return nativeErrorDec;
    }

    public void setNativeErrorDec(String nativeErrorDec) {
        this.nativeErrorDec = nativeErrorDec;
    }

    public String getNativeErrorExt() {
        return nativeErrorExt;
    }

    public void setNativeErrorExt(String nativeErrorExt) {
        this.nativeErrorExt = nativeErrorExt;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getRquid() {
        return rquid;
    }

    public void setRquid(String rquid) {
        this.rquid = rquid;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (errorID != null ? errorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MWErrorLogs)) {
            return false;
        }
        MWErrorLogs other = (MWErrorLogs) object;
        if ((this.errorID == null && other.errorID != null) || (this.errorID != null && !this.errorID.equals( other.errorID ))) {
            return false;
        }
        return true;
    }


}
