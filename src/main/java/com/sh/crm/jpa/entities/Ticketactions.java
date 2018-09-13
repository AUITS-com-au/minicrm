/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author achah
 */
@Entity
@Table(name = "ticketactions")
@XmlRootElement
public class Ticketactions extends BasicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActionID")
    private Integer actionID;
    @Size(max = 150)
    @Column(name = "ArabicLabel")
    private String arabicLabel;
    @Size(max = 150)
    @Column(name = "EnglishLabel")
    private String englishLabel;
    @Column(name = "Enabled")
    private Boolean enabled;

    @Column(name = "SetStatusTo")
    private Integer setStatusTo;

    public Ticketactions() {
    }

    public Ticketactions(Integer actionID) {
        this.actionID = actionID;
    }

    public Integer getActionID() {
        return actionID;
    }

    public void setActionID(Integer actionID) {
        this.actionID = actionID;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Integer getSetStatusTo() {
        return setStatusTo;
    }

    public void setSetStatusTo(Integer setStatusTo) {
        this.setStatusTo = setStatusTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actionID != null ? actionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticketactions)) {
            return false;
        }
        Ticketactions other = (Ticketactions) object;
        if ((this.actionID == null && other.actionID != null) || (this.actionID != null && !this.actionID.equals(other.actionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Ticketactions[ actionID=" + actionID + " ]";
    }

}
