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
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "ticketdata")
@XmlRootElement
public class Ticketdata extends BasicModelWithID {

    @Column(name = "ActionID")
    private Integer actionID;
    @Size(max = 400)
    @Column(name = "Title")
    private String title;
    @Size(max = 2147483647)
    @Column(name = "TicketData")
    private String ticketData;

    @Size(max = 2147483647)
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Hidden")
    private Short hidden;
    @Column(name = "OldStatus")
    private Integer oldStatus;
    @Column(name = "NewStatus")
    private Integer newStatus;
    @Column(name = "OldTopic")
    private Integer oldTopic;
    @Column(name = "NewTopic")
    private Integer newTopic;
    @Column(name = "NotificationID")
    private BigInteger notificationID;
    @Column(name = "Notify")
    private Short notify;
    @Column(name = "NotfiyStatus")
    private Integer notfiyStatus;
    @JoinColumn(name = "TicketID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ticketID;

    public Ticketdata() {
    }

    public Ticketdata(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActionID() {
        return actionID;
    }

    public void setActionID(Integer actionID) {
        this.actionID = actionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicketData() {
        return ticketData;
    }

    public void setTicketData(String ticketData) {
        this.ticketData = ticketData;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Short getHidden() {
        return hidden;
    }

    public void setHidden(Short hidden) {
        this.hidden = hidden;
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

    public BigInteger getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(BigInteger notificationID) {
        this.notificationID = notificationID;
    }

    public Short getNotify() {
        return notify;
    }

    public void setNotify(Short notify) {
        this.notify = notify;
    }

    public Integer getNotfiyStatus() {
        return notfiyStatus;
    }

    public void setNotfiyStatus(Integer notfiyStatus) {
        this.notfiyStatus = notfiyStatus;
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
        if (!(object instanceof Ticketdata)) {
            return false;
        }
        Ticketdata other = (Ticketdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Ticketdata[ id=" + id + " ]";
    }

}
