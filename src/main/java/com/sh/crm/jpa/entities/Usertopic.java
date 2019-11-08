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

/**
 * @author achah
 */
@Entity
@Table(name = "usertopic")
@XmlRootElement
public class Usertopic extends BasicModelWithIDInt {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserID")
    private String userID;

    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private short status;
    @JoinColumn(name = "TopicID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Topic topicID;

    public Usertopic() {
    }

    public Usertopic(Integer id) {
        this.id = id;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Topic getTopicID() {
        return topicID;
    }

    public void setTopicID(Topic topicID) {
        this.topicID = topicID;
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
        if (!(object instanceof Usertopic)) {
            return false;
        }
        Usertopic other = (Usertopic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Usertopic[ id=" + id + " ]";
    }

}
