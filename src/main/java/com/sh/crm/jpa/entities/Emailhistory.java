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
@Table(name = "emailhistory")
@XmlRootElement
public class Emailhistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TicketID")
    private long ticketID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "EmailID")
    private String emailID;
    @Size(max = 2147483647)
    @Column(name = "CopyTo")
    private String copyTo;
    @Size(max = 2147483647)
    @Column(name = "BCopyTo")
    private String bCopyTo;
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "SendDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "SendingOn")
    private String sendingOn;
    @Column(name = "Status")
    private Integer status;
    @Size(max = 50)
    @Column(name = "Type")
    private String type;
    @Size(max = 2147483647)
    @Column(name = "Result")
    private String result;
    @Column(name = "ReadStatus")
    private Short readStatus;
    @Column(name = "DeleteStatus")
    private Short deleteStatus;
    @JoinColumn(name = "EmailMessage", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Emailmessage emailMessage;

    public Emailhistory() {
    }

    public Emailhistory(Long id) {
        this.id = id;
    }

    public Emailhistory(Long id, long ticketID, String emailID, String sendingOn) {
        this.id = id;
        this.ticketID = ticketID;
        this.emailID = emailID;
        this.sendingOn = sendingOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTicketID() {
        return ticketID;
    }

    public void setTicketID(long ticketID) {
        this.ticketID = ticketID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    public String getBCopyTo() {
        return bCopyTo;
    }

    public void setBCopyTo(String bCopyTo) {
        this.bCopyTo = bCopyTo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendingOn() {
        return sendingOn;
    }

    public void setSendingOn(String sendingOn) {
        this.sendingOn = sendingOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Short getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Short readStatus) {
        this.readStatus = readStatus;
    }

    public Short getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Short deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Emailmessage getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(Emailmessage emailMessage) {
        this.emailMessage = emailMessage;
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
        if (!(object instanceof Emailhistory)) {
            return false;
        }
        Emailhistory other = (Emailhistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Emailhistory[ id=" + id + " ]";
    }

}
