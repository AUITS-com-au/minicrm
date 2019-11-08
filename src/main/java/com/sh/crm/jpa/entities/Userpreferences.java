package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "userpreferences")
@XmlRootElement
public class Userpreferences extends BasicModelWithID {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    @JsonIgnore
    private Users userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EmailsNotifications")
    private Boolean emailsNotifications;
    @Size(max = 20)
    @Column(name = "Language")
    private String language;
    @Lob
    @Column(name = "Avatar")
    private byte[] avatar;
    @Column(name = "SLAEmails")
    private Boolean sLAEmails;
    @Column(name = "TicketCreationEmails")
    private Boolean ticketCreationEmails;
    @Column(name = "TicketEditEmails")
    private Boolean ticketEditEmails;
    @Column(name = "TicketAssignEmails")
    private Boolean ticketAssignEmails;
    @Column(name = "TicketCLoseEmails")
    private Boolean ticketCLoseEmails;
    @Column(name = "EmailDigest")
    private Boolean emailDigest;
    @Column(name = "IncludeAttatchments")
    private Boolean includeAttatchments;


    public Userpreferences() {
    }

    public Userpreferences(Long id) {
        this.id = id;
    }

    public Userpreferences(Users userID) {
        this.userID = userID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Boolean getEmailsNotifications() {
        return emailsNotifications;
    }

    public void setEmailsNotifications(Boolean emailsNotifications) {
        this.emailsNotifications = emailsNotifications;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Boolean getSLAEmails() {
        return sLAEmails;
    }

    public void setSLAEmails(Boolean sLAEmails) {
        this.sLAEmails = sLAEmails;
    }

    public Boolean getTicketCreationEmails() {
        return ticketCreationEmails;
    }

    public void setTicketCreationEmails(Boolean ticketCreationEmails) {
        this.ticketCreationEmails = ticketCreationEmails;
    }

    public Boolean getTicketEditEmails() {
        return ticketEditEmails;
    }

    public void setTicketEditEmails(Boolean ticketEditEmails) {
        this.ticketEditEmails = ticketEditEmails;
    }

    public Boolean getTicketAssignEmails() {
        return ticketAssignEmails;
    }

    public void setTicketAssignEmails(Boolean ticketAssignEmails) {
        this.ticketAssignEmails = ticketAssignEmails;
    }

    public Boolean getEmailDigest() {
        return emailDigest;
    }

    public void setEmailDigest(Boolean emailDigest) {
        this.emailDigest = emailDigest;
    }

    public Boolean getIncludeAttatchments() {
        return includeAttatchments;
    }

    public void setIncludeAttatchments(Boolean includeAttatchments) {
        this.includeAttatchments = includeAttatchments;
    }

    public Boolean getTicketCLoseEmails() {
        return ticketCLoseEmails;
    }

    public void setTicketCLoseEmails(Boolean ticketCLoseEmails) {
        this.ticketCLoseEmails = ticketCLoseEmails;
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
        if (!(object instanceof Userpreferences)) {
            return false;
        }
        Userpreferences other = (Userpreferences) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Userpreferences[ id=" + id + " ]";
    }

}
