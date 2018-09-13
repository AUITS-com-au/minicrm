/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author achah
 */
@Entity
@Table(name = "sla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sla.findAll", query = "SELECT s FROM Sla s")})
public class Sla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time")
    private int time;
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 50)
    @Column(name = "CreatedBy")
    private String createdBy;
    @Size(max = 50)
    @Column(name = "ModifiedBy")
    private String modifiedBy;
    @Column(name = "ModificationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;
    @Size(max = 50)
    @Column(name = "SLAName")
    private String sLAName;
    @Column(name = "CatID")
    private Integer catID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slaid", fetch = FetchType.LAZY)
    private List<Topicsla> topicslaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sla", fetch = FetchType.LAZY)
    private List<Escalationhistory> escalationhistoryList;

    public Sla() {
    }

    public Sla(Integer id) {
        this.id = id;
    }

    public Sla(Integer id, int time) {
        this.id = id;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getSLAName() {
        return sLAName;
    }

    public void setSLAName(String sLAName) {
        this.sLAName = sLAName;
    }

    public Integer getCatID() {
        return catID;
    }

    public void setCatID(Integer catID) {
        this.catID = catID;
    }

    @XmlTransient
    public List<Topicsla> getTopicslaList() {
        return topicslaList;
    }

    public void setTopicslaList(List<Topicsla> topicslaList) {
        this.topicslaList = topicslaList;
    }

    @XmlTransient
    public List<Escalationhistory> getEscalationhistoryList() {
        return escalationhistoryList;
    }

    public void setEscalationhistoryList(List<Escalationhistory> escalationhistoryList) {
        this.escalationhistoryList = escalationhistoryList;
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
        if (!(object instanceof Sla)) {
            return false;
        }
        Sla other = (Sla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Sla[ id=" + id + " ]";
    }
    
}
