package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@Table(name = "sla")
@XmlRootElement

public class Sla extends BasicModelWithIDInt {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Time")
    private int time;

    @Size(max = 50)
    @Column(name = "SLAName")
    private String sLAName;
    @Column(name = "CatID")
    private Integer catID;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slaid", fetch = FetchType.LAZY)
    private List<Topicsla> topicslaList;

    public Sla() {
    }

    public Sla(Integer id) {
        this.id = id;
    }

    public Sla(Integer id, int time) {
        this.id = id;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sla)) {
            return false;
        }
        Sla other = (Sla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }


}
