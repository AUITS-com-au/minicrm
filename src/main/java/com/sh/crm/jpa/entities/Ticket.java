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
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author achah
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
public class Ticket {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Column(name = "Topic")
    private Integer topic;
    @Column(name = "OriginalTopic")
    private Integer originalTopic;
    @Column(name = "CurrentStatus")
    private Integer currentStatus;
    @Size(max = 50)
    @Column(name = "CreatedBy")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ModifiedBy")
    private String modifiedBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "ModificationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;
    @Column(name = "CrossedMainSLA")
    private Short crossedMainSLA;
    @Column(name = "CustomerAccount")
    private BigInteger customerAccount;
    @Column(name = "SourceChannel")
    private Integer sourceChannel;
    @Size(max = 300)
    @Column(name = "Subject")
    private String subject;
    @Column(name = "TicketType")
    private Integer ticketType;
    @Size(max = 2147483647)
    @Column(name = "Details")
    private String details;
    @Size(max = 50)
    @Column(name = "AssignedTo")
    private String assignedTo;
    @Column(name = "EscalationCalDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date escalationCalDate;
    @Column(name = "LastSLA")
    private Integer lastSLA;
    @Column(name = "Language")
    private Integer language;
    @Column(name = "Priority")
    private Integer priority;
    @Column(name = "LastTicketData")
    private BigInteger lastTicketData;
    @Column(name = "Solved")
    private Short solved;
    @Column(name = "Closed")
    private Short closed;
    @Column(name = "Deleted")
    private Short deleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketID", fetch = FetchType.LAZY)
    private List<Ticketlock> ticketlockList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketID", fetch = FetchType.LAZY)
    private List<Ticketdata> ticketdataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketID", fetch = FetchType.LAZY)
    private List<Escalationhistory> escalationhistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketID", fetch = FetchType.LAZY)
    private List<SmsHistory> smsHistoryList;

    public Ticket() {
    }

    public Ticket(Long id) {
        this.id = id;
    }

    public Ticket(Long id, String modifiedBy, Date creationDate) {
        this.id = id;
        this.modifiedBy = modifiedBy;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public Integer getOriginalTopic() {
        return originalTopic;
    }

    public void setOriginalTopic(Integer originalTopic) {
        this.originalTopic = originalTopic;
    }

    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Short getCrossedMainSLA() {
        return crossedMainSLA;
    }

    public void setCrossedMainSLA(Short crossedMainSLA) {
        this.crossedMainSLA = crossedMainSLA;
    }

    public BigInteger getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(BigInteger customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Integer getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(Integer sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Date getEscalationCalDate() {
        return escalationCalDate;
    }

    public void setEscalationCalDate(Date escalationCalDate) {
        this.escalationCalDate = escalationCalDate;
    }

    public Integer getLastSLA() {
        return lastSLA;
    }

    public void setLastSLA(Integer lastSLA) {
        this.lastSLA = lastSLA;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigInteger getLastTicketData() {
        return lastTicketData;
    }

    public void setLastTicketData(BigInteger lastTicketData) {
        this.lastTicketData = lastTicketData;
    }

    public Short getSolved() {
        return solved;
    }

    public void setSolved(Short solved) {
        this.solved = solved;
    }

    public Short getClosed() {
        return closed;
    }

    public void setClosed(Short closed) {
        this.closed = closed;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    @XmlTransient
    public List<Ticketlock> getTicketlockList() {
        return ticketlockList;
    }

    public void setTicketlockList(List<Ticketlock> ticketlockList) {
        this.ticketlockList = ticketlockList;
    }

    @XmlTransient
    public List<Ticketdata> getTicketdataList() {
        return ticketdataList;
    }

    public void setTicketdataList(List<Ticketdata> ticketdataList) {
        this.ticketdataList = ticketdataList;
    }

    @XmlTransient
    public List<Escalationhistory> getEscalationhistoryList() {
        return escalationhistoryList;
    }

    public void setEscalationhistoryList(List<Escalationhistory> escalationhistoryList) {
        this.escalationhistoryList = escalationhistoryList;
    }

    @XmlTransient
    public List<SmsHistory> getSmsHistoryList() {
        return smsHistoryList;
    }

    public void setSmsHistoryList(List<SmsHistory> smsHistoryList) {
        this.smsHistoryList = smsHistoryList;
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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Ticket[ id=" + id + " ]";
    }

}
