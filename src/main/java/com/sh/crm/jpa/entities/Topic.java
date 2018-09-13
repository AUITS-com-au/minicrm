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
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author achah
 */
@Entity
@Table(name = "topic")
@XmlRootElement
public class Topic extends BasicModelWithIDInt {


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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "Configuration")
    private String configuration;
    @Column(name = "Enabled")
    private Boolean enabled;
    @Size(max = 2147483647)
    @Column(name = "Principals")
    private String principals;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topicId", fetch = FetchType.LAZY)
    private List<Topicspermissions> topicspermissionsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topicID", fetch = FetchType.LAZY)
    private List<Usertopic> usertopicList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topicID", fetch = FetchType.LAZY)
    private List<Topicsla> topicslaList;
    @JoinColumn(name = "SubCategory", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Subcategory subCategory;

    public Topic() {
    }

    public Topic(Integer id) {
        this.id = id;
    }

    public Topic(Integer id, String arabicLabel, String englishLabel, String configuration) {
        this.id = id;
        this.arabicLabel = arabicLabel;
        this.englishLabel = englishLabel;
        this.configuration = configuration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPrincipals() {
        return principals;
    }

    public void setPrincipals(String principals) {
        this.principals = principals;
    }

    @XmlTransient
    public List<Topicspermissions> getTopicspermissionsList() {
        return topicspermissionsList;
    }

    public void setTopicspermissionsList(List<Topicspermissions> topicspermissionsList) {
        this.topicspermissionsList = topicspermissionsList;
    }

    @XmlTransient
    public List<Usertopic> getUsertopicList() {
        return usertopicList;
    }

    public void setUsertopicList(List<Usertopic> usertopicList) {
        this.usertopicList = usertopicList;
    }

    @XmlTransient
    public List<Topicsla> getTopicslaList() {
        return topicslaList;
    }

    public void setTopicslaList(List<Topicsla> topicslaList) {
        this.topicslaList = topicslaList;
    }

    public Subcategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Subcategory subCategory) {
        this.subCategory = subCategory;
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
        if (!(object instanceof Topic)) {
            return false;
        }
        Topic other = (Topic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Topic[ id=" + id + " ]";
    }

}
