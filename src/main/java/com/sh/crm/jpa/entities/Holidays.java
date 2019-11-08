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
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * @author achah
 */
@Entity
@Table(name = "holidays")
@XmlRootElement
public class Holidays extends BasicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HolidayID")
    private Integer holidayID;
    @Size(max = 250)
    @Column(name = "HolidayName")
    private String holidayName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EndData")
    @Temporal(TemporalType.DATE)
    private Date endData;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Enabled")
    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "holidayID", fetch = FetchType.LAZY)
    private List<Maincatholidays> maincatholidaysList;

    public Holidays() {
    }

    public Holidays(Integer holidayID) {
        this.holidayID = holidayID;
    }

    public Integer getHolidayID() {
        return holidayID;
    }

    public void setHolidayID(Integer holidayID) {
        this.holidayID = holidayID;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndData() {
        return endData;
    }

    public void setEndData(Date endData) {
        this.endData = endData;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public List<Maincatholidays> getMaincatholidaysList() {
        return maincatholidaysList;
    }

    public void setMaincatholidaysList(List<Maincatholidays> maincatholidaysList) {
        this.maincatholidaysList = maincatholidaysList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (holidayID != null ? holidayID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Holidays)) {
            return false;
        }
        Holidays other = (Holidays) object;
        if ((this.holidayID == null && other.holidayID != null) || (this.holidayID != null && !this.holidayID.equals( other.holidayID ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Holidays[ holidayID=" + holidayID + " ]";
    }

}
