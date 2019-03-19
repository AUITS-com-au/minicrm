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
@Table(name = "logindetails")
@XmlRootElement
public class Logindetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserID")
    private String userID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Result")
    private short result;
    @Size(max = 20)
    @Column(name = "IPAddress")
    private String iPAddress;
    @Column(name = "LogoutDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDateTime;
    @Size(max = 500)
    @Column(name = "Token")
    private String token;
    @Size(max = 100)
    @Column(name = "LogoutReason")
    private String logoutReason;

    public Logindetails() {
    }

    public Logindetails(Long id) {
        this.id = id;
    }

    public Logindetails(Long id, String userID, Date dateTime, short result) {
        this.id = id;
        this.userID = userID;
        this.dateTime = dateTime;
        this.result = result;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public short getResult() {
        return result;
    }

    public void setResult(short result) {
        this.result = result;
    }

    public String getIPAddress() {
        return iPAddress;
    }

    public void setIPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public Date getLogoutDateTime() {
        return logoutDateTime;
    }

    public void setLogoutDateTime(Date logoutDateTime) {
        this.logoutDateTime = logoutDateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogoutReason() {
        return logoutReason;
    }

    public void setLogoutReason(String logoutReason) {
        this.logoutReason = logoutReason;
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
        if (!(object instanceof Logindetails)) {
            return false;
        }
        Logindetails other = (Logindetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Logindetails[ id=" + id + " ]";
    }

}
