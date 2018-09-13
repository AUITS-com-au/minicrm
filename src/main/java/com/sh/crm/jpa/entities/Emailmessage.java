/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author achah
 */
@Entity
@Table(name = "emailmessage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emailmessage.findAll", query = "SELECT e FROM Emailmessage e")})
public class Emailmessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "EmailTitle")
    private String emailTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "EmailMessage")
    private String emailMessage;
    @OneToMany(mappedBy = "emailMessage", fetch = FetchType.LAZY)
    private List<Emailhistory> emailhistoryList;

    public Emailmessage() {
    }

    public Emailmessage(Long id) {
        this.id = id;
    }

    public Emailmessage(Long id, String emailTitle, String emailMessage) {
        this.id = id;
        this.emailTitle = emailTitle;
        this.emailMessage = emailMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    @XmlTransient
    public List<Emailhistory> getEmailhistoryList() {
        return emailhistoryList;
    }

    public void setEmailhistoryList(List<Emailhistory> emailhistoryList) {
        this.emailhistoryList = emailhistoryList;
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
        if (!(object instanceof Emailmessage)) {
            return false;
        }
        Emailmessage other = (Emailmessage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Emailmessage[ id=" + id + " ]";
    }
    
}
