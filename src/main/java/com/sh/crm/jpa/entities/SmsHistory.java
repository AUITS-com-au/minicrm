/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "smsHistory")
@XmlRootElement
public class SmsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Mobile")
    private String mobile;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "Message")
    private String message;
    @Column(name = "TemplateID")
    private Integer templateID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private int status;
    @Column(name = "SendDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    @Size(max = 50)
    @Column(name = "SendingON")
    private String sendingON;
    @Size(max = 2147483647)
    @Column(name = "ErrorCode")
    private String errorCode;
    @Size(max = 50)
    @Column(name = "BackEndID")
    private String backEndID;
    @JoinColumn(name = "TicketID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ticketID;

    public SmsHistory() {
    }

    public SmsHistory(Long id) {
        this.id = id;
    }

    public SmsHistory(Long id, String mobile, Date creationDate, String message, int status) {
        this.id = id;
        this.mobile = mobile;
        this.creationDate = creationDate;
        this.message = message;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTemplateID() {
        return templateID;
    }

    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendingON() {
        return sendingON;
    }

    public void setSendingON(String sendingON) {
        this.sendingON = sendingON;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getBackEndID() {
        return backEndID;
    }

    public void setBackEndID(String backEndID) {
        this.backEndID = backEndID;
    }

    public Ticket getTicketID() {
        return ticketID;
    }

    public void setTicketID(Ticket ticketID) {
        this.ticketID = ticketID;
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
        if (!(object instanceof SmsHistory)) {
            return false;
        }
        SmsHistory other = (SmsHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.SmsHistory[ id=" + id + " ]";
    }

}
