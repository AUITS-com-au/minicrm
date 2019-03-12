/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "ticketlock")
@XmlRootElement
public class Ticketlock {
    static final String dtfPattern = "yyyy-MM-dd hh:mm:ss a";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LockID")
    private Long lockID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserID")
    private String userID;

    @Column(name = "DateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dtfPattern)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ExpiresOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresOn;
    @JoinColumn(name = "TicketID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ticketID;

    @Column(name = "Expired")
    private boolean expired;

    public Ticketlock() {
    }

    public Ticketlock(Long lockID) {
        this.lockID = lockID;
    }

    public Ticketlock(Long lockID, String userID, Date dateTime, Date expiresOn) {
        this.lockID = lockID;
        this.userID = userID;
        this.dateTime = dateTime;
        this.expiresOn = expiresOn;
    }

    public Long getLockID() {
        return lockID;
    }

    public void setLockID(Long lockID) {
        this.lockID = lockID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Ticket getTicketID() {
        return ticketID;
    }

    public void setTicketID(Ticket ticketID) {
        this.ticketID = ticketID;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lockID != null ? lockID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticketlock)) {
            return false;
        }
        Ticketlock other = (Ticketlock) object;
        if ((this.lockID == null && other.lockID != null) || (this.lockID != null && !this.lockID.equals( other.lockID ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticketlock{" +
                "lockID=" + lockID +
                ", userID='" + userID + '\'' +
                ", dateTime=" + dateTime +
                ", expiresOn=" + expiresOn +
                ", ticketID=" + ticketID +
                ", expired=" + expired +
                '}';
    }
}
