
package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "ticketactions")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @JsonIgnore
    @ManyToOne(targetEntity = Status.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "SetStatusTo")
    private Status setStatusTo;

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

    public Status getSetStatusTo() {
        return setStatusTo;
    }

    public void setSetStatusTo(Status setStatusTo) {
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

        if (!(object instanceof Ticketactions)) {
            return false;
        }
        Ticketactions other = (Ticketactions) object;
        if ((this.actionID == null && other.actionID != null) || (this.actionID != null && !this.actionID.equals( other.actionID ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticketactions{" +
                "actionID=" + actionID +
                ", arabicLabel='" + arabicLabel + '\'' +
                ", englishLabel='" + englishLabel + '\'' +
                ", enabled=" + enabled +
                ", setStatusTo=" + setStatusTo +
                ", createdBy='" + createdBy + '\'' +
                ", modificationDate=" + modificationDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
