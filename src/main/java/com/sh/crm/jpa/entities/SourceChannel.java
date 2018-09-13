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
@Table(name = "sourceChannel")
@XmlRootElement
public class SourceChannel extends BasicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ChannelID")
    private Integer channelID;
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
    private Boolean enabled;

    public SourceChannel() {
    }

    public SourceChannel(Integer channelID) {
        this.channelID = channelID;
    }

    public SourceChannel(Integer channelID, String arabicLabel, String englishLabel) {
        this.channelID = channelID;
        this.arabicLabel = arabicLabel;
        this.englishLabel = englishLabel;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (channelID != null ? channelID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SourceChannel)) {
            return false;
        }
        SourceChannel other = (SourceChannel) object;
        if ((this.channelID == null && other.channelID != null) || (this.channelID != null && !this.channelID.equals(other.channelID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.SourceChannel[ channelID=" + channelID + " ]";
    }

}
