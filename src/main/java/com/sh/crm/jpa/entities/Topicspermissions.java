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
@Table(name = "topicspermissions")
@XmlRootElement
public class Topicspermissions extends BasicModelWithID {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "assigne")
    private String assigne;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "type")
    private String type;
    @Column(name = "admin")
    private Short admin;
    @Column(name = "canCreate")
    private Short canCreate;
    @Column(name = "canRead")
    private Short canRead;
    @Column(name = "canDelete")
    private Short canDelete;
    @Column(name = "canWrite")
    private Short canWrite;
    @Column(name = "canRunReport")
    private Short canRunReport;

    @JoinColumn(name = "topicId", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Topic topicId;

    public Topicspermissions() {
    }

    public Topicspermissions(Long id) {
        this.id = id;
    }

    public Topicspermissions(Long id, String assigne, String type) {
        this.id = id;
        this.assigne = assigne;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Short getAdmin() {
        return admin;
    }

    public void setAdmin(Short admin) {
        this.admin = admin;
    }

    public Short getCanCreate() {
        return canCreate;
    }

    public void setCanCreate(Short canCreate) {
        this.canCreate = canCreate;
    }

    public Short getCanRead() {
        return canRead;
    }

    public void setCanRead(Short canRead) {
        this.canRead = canRead;
    }

    public Short getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Short canDelete) {
        this.canDelete = canDelete;
    }

    public Short getCanWrite() {
        return canWrite;
    }

    public void setCanWrite(Short canWrite) {
        this.canWrite = canWrite;
    }

    public Short getCanRunReport() {
        return canRunReport;
    }

    public void setCanRunReport(Short canRunReport) {
        this.canRunReport = canRunReport;
    }

    public Topic getTopicId() {
        return topicId;
    }

    public void setTopicId(Topic topicId) {
        this.topicId = topicId;
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
        if (!(object instanceof Topicspermissions)) {
            return false;
        }
        Topicspermissions other = (Topicspermissions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Topicspermissions[ id=" + id + " ]";
    }

}
