/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author achah
 */
@Entity
@Table(name = "maincatholidays")
@XmlRootElement
public class Maincatholidays {
    // private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @JoinColumn(name = "HolidayID", referencedColumnName = "HolidayID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Holidays holidayID;
    @JoinColumn(name = "MainCategory", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Maincategory mainCategory;

    public Maincatholidays() {
    }

    public Maincatholidays(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Holidays getHolidayID() {
        return holidayID;
    }

    public void setHolidayID(Holidays holidayID) {
        this.holidayID = holidayID;
    }

    public Maincategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Maincategory mainCategory) {
        this.mainCategory = mainCategory;
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
        if (!(object instanceof Maincatholidays)) {
            return false;
        }
        Maincatholidays other = (Maincatholidays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Maincatholidays[ id=" + id + " ]";
    }

}
