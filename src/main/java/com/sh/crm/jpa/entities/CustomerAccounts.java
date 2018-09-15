
package com.sh.crm.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@Table(name = "CustomerAccounts")
@XmlRootElement
public class CustomerAccounts extends BasicModelWithID {

    @Size(max = 250)
    @Column(name = "CustomerNameAR")
    private String customerNameAR;
    @Size(max = 250)
    @Column(name = "CustomerNameEn")
    private String customerNameEn;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @Size(max = 15)
    @Column(name = "Mobile")
    private String mobile;
    @Size(max = 15)
    @Column(name = "Nin")
    private String nin;
    @Size(max = 50)
    @Column(name = "Segment")
    private String segment;

    public CustomerAccounts() {
    }

    public CustomerAccounts(Long id) {
        this.id = id;
    }

    public String getCustomerNameAR() {
        return customerNameAR;
    }

    public void setCustomerNameAR(String customerNameAR) {
        this.customerNameAR = customerNameAR;
    }

    public String getCustomerNameEn() {
        return customerNameEn;
    }

    public void setCustomerNameEn(String customerNameEn) {
        this.customerNameEn = customerNameEn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
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
        if (!(object instanceof CustomerAccounts)) {
            return false;
        }
        CustomerAccounts other = (CustomerAccounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.CustomerAccounts[ id=" + id + " ]";
    }

}
