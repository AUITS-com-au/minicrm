/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

/**
 * @author achah
 */
@Entity
@Table(name = "assignedtickets")
@XmlRootElement
public class Assignedtickets extends BasicModelWithID {

    @Column(name = "ticketID")
    private BigInteger ticketID;
    @Size(max = 50)
    @Column(name = "userID")
    private String userID;
    @Column(name = "Enabled")
    private Integer enabled;


    public Assignedtickets() {
    }

    public Assignedtickets(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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
        if (!(object instanceof Assignedtickets)) {
            return false;
        }
        Assignedtickets other = (Assignedtickets) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Assignedtickets[ id=" + id + " ]";
    }

}
