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
@Table(name = "tickettypes")
@XmlRootElement
public class Tickettypes extends BasicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeID")
    private Integer typeID;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ArabicLabel")
    private String arabicLabel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "EnglishLabel")
    private String englishLabel;
    @Column(name = "Enabled")
    private Short enabled;

    public Tickettypes() {
    }

    public Tickettypes(Integer typeID) {
        this.typeID = typeID;
    }

    public Tickettypes(Integer typeID, String arabicLabel, String englishLabel) {
        this.typeID = typeID;
        this.arabicLabel = arabicLabel;
        this.englishLabel = englishLabel;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public String getArabicLabel() {
        return arabicLabel;
    }

    public void setArabicLabel(String arabicLabel) {
        this.arabicLabel = arabicLabel;
    }

    public String getEnglishLabel() {
        return englishLabel;
    }

    public void setEnglishLabel(String englishLabel) {
        this.englishLabel = englishLabel;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeID != null ? typeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tickettypes)) {
            return false;
        }
        Tickettypes other = (Tickettypes) object;
        if ((this.typeID == null && other.typeID != null) || (this.typeID != null && !this.typeID.equals( other.typeID ))) {
            return false;
        }
        return true;
    }


}
