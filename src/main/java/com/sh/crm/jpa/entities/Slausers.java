/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author achah
 */
@Entity
@Table(name = "slausers")
@XmlRootElement
public class Slausers extends BasicModelWithID {


    @Size(max = 50)
    @Column(name = "UserId")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Topicsla")
    private int topicSLA;
    @Basic(optional = false)
    @Column(name = "Enabled")
    private Boolean enabled = true;
    @Size(max = 2147483647)
    @Column(name = "Emails")
    private String emails;
    @Size(max = 2147483647)
    @Column(name = "MobileNumbers")
    private String mobileNumbers;

    public Slausers() {
    }

    public Slausers(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public int getTopicSLA() {
        return topicSLA;
    }

    public void setTopicSLA(int topicSLA) {
        this.topicSLA = topicSLA;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(String mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
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
        if (!(object instanceof Slausers)) {
            return false;
        }
        Slausers other = (Slausers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Slausers[ id=" + id + " ]";
    }

}
