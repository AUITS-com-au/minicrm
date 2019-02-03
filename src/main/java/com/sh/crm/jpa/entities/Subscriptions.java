
package com.sh.crm.jpa.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "subscriptions")
@XmlRootElement
public class Subscriptions extends BasicModelWithID {

    @Column(name = "Topic")
    private Integer topic;

    @Column(name = "TicketID")
    private Long ticketID;
    @Size(max = 50)
    @Column(name = "userID")
    private String userID;
    @Column(name = "subStatus")
    private Boolean subStatus;
    @Column(name = "OnCreate")
    private Boolean onCreate;
    @Column(name = "OnUpdate")
    private Boolean onUpdate;
    @Column(name = "OnClose")
    private Boolean onClose;
    @Size(max = 250)
    @Column(name = "EmailID")
    private String emailID;

    public Subscriptions() {
    }

    public Subscriptions(Long id) {
        this.id = id;
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


    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Boolean getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Boolean subStatus) {
        this.subStatus = subStatus;
    }

    public Boolean getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(Boolean onCreate) {
        this.onCreate = onCreate;
    }

    public Boolean getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(Boolean onUpdate) {
        this.onUpdate = onUpdate;
    }

    public Boolean getOnClose() {
        return onClose;
    }

    public void setOnClose(Boolean onClose) {
        this.onClose = onClose;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
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
        if (!(object instanceof Subscriptions)) {
            return false;
        }
        Subscriptions other = (Subscriptions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Subscriptions[ id=" + id + " ]";
    }
    
}
