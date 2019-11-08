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
@Table(name = "grouproles")
@XmlRootElement
@EntityListeners(AuditingEntityListener.class)
public class Grouproles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "groupID")
    @ManyToOne
    private Groups groupID;
    @ManyToOne
    @JoinColumn(name = "roleID")
    private Roles roleID;
    @CreatedDate
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @CreatedBy
    @Column(name = "CreatedBy")
    private String createdBy;

    public Grouproles() {
    }

    public Grouproles(Integer id) {
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

    public Roles getRoleID() {
        return roleID;
    }

    public void setRoleID(Roles roleID) {
        this.roleID = roleID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        if (!(object instanceof Grouproles)) {
            return false;
        }
        Grouproles other = (Grouproles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Grouproles[ id=" + id + " ]";
    }

}
