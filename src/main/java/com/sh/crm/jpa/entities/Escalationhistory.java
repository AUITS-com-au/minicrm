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
@Table(name = "escalationhistory")
@XmlRootElement
public class Escalationhistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESCLevel")
    private int eSCLevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESCDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eSCDateTime;
    @Size(max = 2147483647)
    @Column(name = "EscUsers")
    private String escUsers;
    @Size(max = 2147483647)
    @Column(name = "EscEmails")
    private String escEmails;
    @JoinColumn(name = "SLA", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sla sla;
    @JoinColumn(name = "TicketID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ticketID;

    public Escalationhistory() {
    }

    public Escalationhistory(Long id) {
        this.id = id;
    }

    public Escalationhistory(Long id, int eSCLevel, Date eSCDateTime) {
        this.id = id;
        this.eSCLevel = eSCLevel;
        this.eSCDateTime = eSCDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getESCLevel() {
        return eSCLevel;
    }

    public void setESCLevel(int eSCLevel) {
        this.eSCLevel = eSCLevel;
    }

    public Date getESCDateTime() {
        return eSCDateTime;
    }

    public void setESCDateTime(Date eSCDateTime) {
        this.eSCDateTime = eSCDateTime;
    }

    public String getEscUsers() {
        return escUsers;
    }

    public void setEscUsers(String escUsers) {
        this.escUsers = escUsers;
    }

    public String getEscEmails() {
        return escEmails;
    }

    public void setEscEmails(String escEmails) {
        this.escEmails = escEmails;
    }

    public Sla getSla() {
        return sla;
    }

    public void setSla(Sla sla) {
        this.sla = sla;
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
        if (!(object instanceof Escalationhistory)) {
            return false;
        }
        Escalationhistory other = (Escalationhistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Escalationhistory[ id=" + id + " ]";
    }

}
