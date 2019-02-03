package com.sh.crm.jpa.entities;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author ahmed shaheen
 */
@Entity
@Table(name = "TicketHistory")
@XmlRootElement
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TicketID")
    private long ticketID;

    @Size(max = 50)
    @Column(name = "CreatedBy")
    private String createdBy;
    @Column(name = "ActionID")
    private Integer actionID;
    @Column(name = "OldStatus")
    private Integer oldStatus;
    @Column(name = "NewStatus")
    private Integer newStatus;
    @Column(name = "OldTopic")
    private Integer oldTopic;
    @Column(name = "NewTopic")
    private Integer newTopic;
    @Column(name = "OldAssigne")
    private String oldAssigne;
    @Column(name = "NewAssigne")
    private String newAssigne;
    @Column(name = "CreationDate")
    protected Date creationDate;

    @Column(name = "DataID")
    private Long dataID;

    @Column(name = "EscalationHisID")
    private Long escalationHisID;

    public TicketHistory() {
    }

    public TicketHistory(Long id) {
        this.id = id;
    }

    public TicketHistory(long ticketID) {

        this.ticketID = ticketID;

    }

    public long getTicketID() {
        return ticketID;
    }

    public void setTicketID(long ticketID) {
        this.ticketID = ticketID;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getActionID() {
        return actionID;
    }

    public void setActionID(Integer actionID) {
        this.actionID = actionID;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Integer getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Integer newStatus) {
        this.newStatus = newStatus;
    }

    public Integer getOldTopic() {
        return oldTopic;
    }

    public void setOldTopic(Integer oldTopic) {
        this.oldTopic = oldTopic;
    }

    public Integer getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(Integer newTopic) {
        this.newTopic = newTopic;
    }

    public String getOldAssigne() {
        return oldAssigne;
    }

    public void setOldAssigne(String oldAssigne) {
        this.oldAssigne = oldAssigne;
    }

    public String getNewAssigne() {
        return newAssigne;
    }

    public void setNewAssigne(String newAssigne) {
        this.newAssigne = newAssigne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getDataID() {
        return dataID;
    }

    public void setDataID(Long dataID) {
        this.dataID = dataID;
    }

    public Long getEscalationHisID() {
        return escalationHisID;
    }

    public void setEscalationHisID(Long escalationHisID) {
        this.escalationHisID = escalationHisID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TicketHistory)) {
            return false;
        }
        TicketHistory other = (TicketHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }


}
