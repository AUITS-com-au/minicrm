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
@Table(name = "userpreferences")
@XmlRootElement
public class Userpreferences extends BasicModelWithID {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserID")
    private String userID;

    @Basic(optional = false)
    @NotNull
    @Column(name = "EmailsNotifications")
    private short emailsNotifications;
    @Size(max = 20)
    @Column(name = "Language")
    private String language;
    @Lob
    @Column(name = "Avatar")
    private byte[] avatar;
    @Column(name = "SLAEmails")
    private Short sLAEmails;
    @Column(name = "TicketCreationEmails")
    private Short ticketCreationEmails;
    @Column(name = "TicketEditEmails")
    private Short ticketEditEmails;
    @Column(name = "TicketAssignEmails")
    private Short ticketAssignEmails;
    @Column(name = "EmailDigest")
    private Short emailDigest;
    @Column(name = "IncludeAttatchments")
    private Short includeAttatchments;
    @Column(name = "AutoSubOnCreate")
    private Short autoSubOnCreate;
    @Column(name = "AutoSubOnClose")
    private Short autoSubOnClose;
    @Column(name = "AutoSubOnEdit")
    private Short autoSubOnEdit;
    @Column(name = "AutoSubOnAssign")
    private Short autoSubOnAssign;

    public Userpreferences() {
    }

    public Userpreferences(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

      public short getEmailsNotifications() {
        return emailsNotifications;
    }

    public void setEmailsNotifications(short emailsNotifications) {
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

    public Short getSLAEmails() {
        return sLAEmails;
    }

    public void setSLAEmails(Short sLAEmails) {
        this.sLAEmails = sLAEmails;
    }

    public Short getTicketCreationEmails() {
        return ticketCreationEmails;
    }

    public void setTicketCreationEmails(Short ticketCreationEmails) {
        this.ticketCreationEmails = ticketCreationEmails;
    }

    public Short getTicketEditEmails() {
        return ticketEditEmails;
    }

    public void setTicketEditEmails(Short ticketEditEmails) {
        this.ticketEditEmails = ticketEditEmails;
    }

    public Short getTicketAssignEmails() {
        return ticketAssignEmails;
    }

    public void setTicketAssignEmails(Short ticketAssignEmails) {
        this.ticketAssignEmails = ticketAssignEmails;
    }

    public Short getEmailDigest() {
        return emailDigest;
    }

    public void setEmailDigest(Short emailDigest) {
        this.emailDigest = emailDigest;
    }

    public Short getIncludeAttatchments() {
        return includeAttatchments;
    }

    public void setIncludeAttatchments(Short includeAttatchments) {
        this.includeAttatchments = includeAttatchments;
    }

    public Short getAutoSubOnCreate() {
        return autoSubOnCreate;
    }

    public void setAutoSubOnCreate(Short autoSubOnCreate) {
        this.autoSubOnCreate = autoSubOnCreate;
    }

    public Short getAutoSubOnClose() {
        return autoSubOnClose;
    }

    public void setAutoSubOnClose(Short autoSubOnClose) {
        this.autoSubOnClose = autoSubOnClose;
    }

    public Short getAutoSubOnEdit() {
        return autoSubOnEdit;
    }

    public void setAutoSubOnEdit(Short autoSubOnEdit) {
        this.autoSubOnEdit = autoSubOnEdit;
    }

    public Short getAutoSubOnAssign() {
        return autoSubOnAssign;
    }

    public void setAutoSubOnAssign(Short autoSubOnAssign) {
        this.autoSubOnAssign = autoSubOnAssign;
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Userpreferences[ id=" + id + " ]";
    }

}
