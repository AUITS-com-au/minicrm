/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "usergroups")
@XmlRootElement
@EntityListeners(AuditingEntityListener.class)
public class Usergroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Integer id;
    @Column(name = "CreatedBy")
    @CreatedBy
    protected String createdBy;
    @CreatedDate
    @Column(name = "CreationDate")
    protected Date creationDate;
    @ManyToOne
    @JoinColumn(name = "groupID")
    private Groups groupID;
    @ManyToOne
    @JoinColumn(name = "userID")
    private Users userID;

    public Usergroups() {
    }

    public Usergroups(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Groups getGroupID() {
        return groupID;
    }

    public void setGroupID(Groups groupID) {
        this.groupID = groupID;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
        if (!(object instanceof Usergroups)) {
            return false;
        }
        Usergroups other = (Usergroups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Usergroups[ id=" + id + " ]";
    }

}
