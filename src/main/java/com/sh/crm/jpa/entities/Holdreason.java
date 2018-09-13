/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author achah
 */
@Entity
@Table(name = "holdreason")
@XmlRootElement
public class Holdreason extends BasicModel {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReasonID")
    private Integer reasonID;
    @Column(name = "SortOrder")
    private Integer sortOrder;
    @Size(max = 150)
    @Column(name = "ArabicLabel")
    private String arabicLabel;
    @Size(max = 150)
    @Column(name = "EnglishLabel")
    private String englishLabel;

    @Column(name = "HoldTime")
    private Integer holdTime;
    @Column(name = "Flexible")
    private Boolean flexible;

    public Holdreason() {
    }

    public Holdreason(Integer reasonID) {
        this.reasonID = reasonID;
    }

    public Integer getReasonID() {
        return reasonID;
    }

    public void setReasonID(Integer reasonID) {
        this.reasonID = reasonID;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

    
    public Integer getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Integer holdTime) {
        this.holdTime = holdTime;
    }

    public Boolean getFlexible() {
        return flexible;
    }

    public void setFlexible(Boolean flexible) {
        this.flexible = flexible;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reasonID != null ? reasonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Holdreason)) {
            return false;
        }
        Holdreason other = (Holdreason) object;
        if ((this.reasonID == null && other.reasonID != null) || (this.reasonID != null && !this.reasonID.equals(other.reasonID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Holdreason[ reasonID=" + reasonID + " ]";
    }

}
