/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author achah
 */
@Entity
@Table(name = "countrycode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countrycode.findAll", query = "SELECT c FROM Countrycode c")})
public class Countrycode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "CountryName")
    private String countryName;
    @Size(max = 255)
    @Column(name = "ISO2")
    private String iso2;
    @Size(max = 255)
    @Column(name = "ISO3")
    private String iso3;
    @Size(max = 255)
    @Column(name = "TopLevelDomain")
    private String topLevelDomain;
    @Size(max = 255)
    @Column(name = "FIPS")
    private String fips;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ISONumeric")
    private Double iSONumeric;
    @Column(name = "GeoNameID")
    private Double geoNameID;
    @Column(name = "E164")
    private Double e164;
    @Size(max = 255)
    @Column(name = "PhoneCode")
    private String phoneCode;
    @Size(max = 255)
    @Column(name = "Continent")
    private String continent;
    @Size(max = 255)
    @Column(name = "Capital")
    private String capital;
    @Size(max = 255)
    @Column(name = "TimeZoneinCapital")
    private String timeZoneinCapital;
    @Size(max = 255)
    @Column(name = "Currency")
    private String currency;
    @Size(max = 255)
    @Column(name = "LanguageCodes")
    private String languageCodes;
    @Size(max = 255)
    @Column(name = "Languages")
    private String languages;
    @Column(name = "AreaKM2")
    private Double areaKM2;
    @Column(name = "InternetHosts")
    private Double internetHosts;
    @Column(name = "InternetUsers")
    private Double internetUsers;
    @Column(name = "Mobile")
    private Double mobile;
    @Column(name = "Landline")
    private Double landline;
    @Column(name = "GDP")
    private Double gdp;
    @Column(name = "MobileLength")
    private Integer mobileLength;
    @Column(name = "Enabled")
    private Short enabled;

    public Countrycode() {
    }

    public Countrycode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(String topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public String getFips() {
        return fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    public Double getISONumeric() {
        return iSONumeric;
    }

    public void setISONumeric(Double iSONumeric) {
        this.iSONumeric = iSONumeric;
    }

    public Double getGeoNameID() {
        return geoNameID;
    }

    public void setGeoNameID(Double geoNameID) {
        this.geoNameID = geoNameID;
    }

    public Double getE164() {
        return e164;
    }

    public void setE164(Double e164) {
        this.e164 = e164;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getTimeZoneinCapital() {
        return timeZoneinCapital;
    }

    public void setTimeZoneinCapital(String timeZoneinCapital) {
        this.timeZoneinCapital = timeZoneinCapital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguageCodes() {
        return languageCodes;
    }

    public void setLanguageCodes(String languageCodes) {
        this.languageCodes = languageCodes;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Double getAreaKM2() {
        return areaKM2;
    }

    public void setAreaKM2(Double areaKM2) {
        this.areaKM2 = areaKM2;
    }

    public Double getInternetHosts() {
        return internetHosts;
    }

    public void setInternetHosts(Double internetHosts) {
        this.internetHosts = internetHosts;
    }

    public Double getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(Double internetUsers) {
        this.internetUsers = internetUsers;
    }

    public Double getMobile() {
        return mobile;
    }

    public void setMobile(Double mobile) {
        this.mobile = mobile;
    }

    public Double getLandline() {
        return landline;
    }

    public void setLandline(Double landline) {
        this.landline = landline;
    }

    public Double getGdp() {
        return gdp;
    }

    public void setGdp(Double gdp) {
        this.gdp = gdp;
    }

    public Integer getMobileLength() {
        return mobileLength;
    }

    public void setMobileLength(Integer mobileLength) {
        this.mobileLength = mobileLength;
    }

    public Short getEnabled() {
        return enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
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
        if (!(object instanceof Countrycode)) {
            return false;
        }
        Countrycode other = (Countrycode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Countrycode[ id=" + id + " ]";
    }
    
}
