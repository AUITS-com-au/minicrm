/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "userroles")
@XmlRootElement
public class Userroles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userID")
    private Users userID;


    @ManyToOne
    @JoinColumn(name = "roleID")
    private Roles roleID;
    @Basic(optional = false)
    @CreatedDate
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @CreatedBy
    @Size(min = 1, max = 50)
    @Column(name = "CreatedBy")
    private String createdBy;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userroles1", fetch = FetchType.LAZY)
    private Userroles userroles;
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Userroles userroles1;

    public Userroles() {
    }

    public Userroles(Long id) {
        this.id = id;
    }

    public Userroles(Long id, Users userID, Roles roleID, Date creationDate, String createdBy) {
        this.id = id;
        this.userID = userID;
        this.roleID = roleID;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
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

    public Userroles getUserroles() {
        return userroles;
    }

    public void setUserroles(Userroles userroles) {
        this.userroles = userroles;
    }

    public Userroles getUserroles1() {
        return userroles1;
    }

    public void setUserroles1(Userroles userroles1) {
        this.userroles1 = userroles1;
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
        if (!(object instanceof Userroles)) {
            return false;
        }
        Userroles other = (Userroles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Userroles[ id=" + id + " ]";
    }

}
