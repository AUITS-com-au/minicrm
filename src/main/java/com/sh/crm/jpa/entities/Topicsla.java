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
@Table(name = "topicsla")
@XmlRootElement
public class Topicsla extends BasicModelWithIDInt {

    @Basic(optional = false)
    @NotNull
    @Column(name = "SLALevel")
    private int sLALevel;

    @Size(max = 500)
    @Column(name = "SLAImpl")
    private String sLAImpl;
    @JoinColumn(name = "SLAID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sla slaid;
    @JoinColumn(name = "TopicID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Topic topicID;

    public Topicsla() {
    }

    public Topicsla(Integer id) {
        this.id = id;
    }

    public Topicsla(Integer id, int sLALevel) {
        this.id = id;
        this.sLALevel = sLALevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSLALevel() {
        return sLALevel;
    }

    public void setSLALevel(int sLALevel) {
        this.sLALevel = sLALevel;
    }

    public String getSLAImpl() {
        return sLAImpl;
    }

    public void setSLAImpl(String sLAImpl) {
        this.sLAImpl = sLAImpl;
    }

    public Sla getSlaid() {
        return slaid;
    }

    public void setSlaid(Sla slaid) {
        this.slaid = slaid;
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
        if (!(object instanceof Topicsla)) {
            return false;
        }
        Topicsla other = (Topicsla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Topicsla[ id=" + id + " ]";
    }

}
