/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import java.io.Serializable;
import java.math.BigInteger;
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

/**
 *
 * @author achah
 */
@Entity
@Table(name = "subscriptions")
@XmlRootElement
public class Subscriptions extends BasicModelWithID {

    @Column(name = "Topic")
    private Integer topic;
    @Column(name = "SubCategory")
    private Integer subCategory;
    @Column(name = "MainCat")
    private Integer mainCat;
    @Column(name = "TicketID")
    private BigInteger ticketID;
    @Size(max = 50)
    @Column(name = "userID")
    private String userID;
    @Column(name = "subStatus")
    private Short subStatus;
    @Column(name = "OnCreate")
    private Short onCreate;
    @Column(name = "OnUpdate")
    private Short onUpdate;
    @Column(name = "OnClose")
    private Short onClose;
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

    public Integer getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Integer subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getMainCat() {
        return mainCat;
    }

    public void setMainCat(Integer mainCat) {
        this.mainCat = mainCat;
    }

    public BigInteger getTicketID() {
        return ticketID;
    }

    public void setTicketID(BigInteger ticketID) {
        this.ticketID = ticketID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Short getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(Short subStatus) {
        this.subStatus = subStatus;
    }

    public Short getOnCreate() {
        return onCreate;
    }

    public void setOnCreate(Short onCreate) {
        this.onCreate = onCreate;
    }

    public Short getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(Short onUpdate) {
        this.onUpdate = onUpdate;
    }

    public Short getOnClose() {
        return onClose;
    }

    public void setOnClose(Short onClose) {
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
