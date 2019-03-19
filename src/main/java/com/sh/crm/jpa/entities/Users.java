package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name = "users")
@XmlRootElement
public class Users extends BasicModelWithIDInt {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserID")
    private String userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "FirstName")
    private String firstName;
    @Size(max = 500)
    @Column(name = "LastName")
    private String lastName;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
    @Size(max = 250)
    @Column(name = "Email")
    private String email;
    @JsonIgnore
    @Size(max = 256)
    @Column(name = "Password")
    private String password;
    @Size(max = 20)
    @Column(name = "StaffID")
    private String staffID;
    @Column(name = "Enabled")
    private Boolean enabled;


    @Column(name = "LoginAttempts")
    private Integer loginAttempts;
    @Column(name = "SystemUser")
    private Boolean systemUser;
    @Size(max = 150)
    @Column(name = "Title")
    private String title;
    @Size(max = 50)
    @Column(name = "Department")
    private String department;
    @Column(name = "LDAPUser")
    private Boolean lDAPUser;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "userID")
    private Userpreferences preferences;
    @Transient
    private List<Permissions> authorities;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String userID, String firstName) {
        this.id = id;
        this.userID = userID;
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Boolean getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(Boolean systemUser) {
        this.systemUser = systemUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getLDAPUser() {
        return lDAPUser;
    }

    public void setLDAPUser(Boolean lDAPUser) {
        this.lDAPUser = lDAPUser;
    }


    public Userpreferences getPreferences() {
        return preferences;
    }


    public void setPreferences(Userpreferences preferences) {
        this.preferences = preferences;
    }

    public List<Permissions> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Permissions> authorities) {
        this.authorities = authorities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", staffID='" + staffID + '\'' +
                ", enabled=" + enabled +
                ", loginAttempts=" + loginAttempts +
                ", systemUser=" + systemUser +
                ", title='" + title + '\'' +
                ", department='" + department + '\'' +
                ", lDAPUser=" + lDAPUser +
                ", preferences=" + preferences +
                ", id=" + id +
                ", createdBy='" + createdBy + '\'' +
                ", modificationDate=" + modificationDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
