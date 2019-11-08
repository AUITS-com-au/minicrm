/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "subcategory")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subcategory extends BasicModelWithIDInt {

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
    @Column(name = "Enabled")
    private Boolean enabled;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subCategory", fetch = FetchType.LAZY)
    private List<Topic> topicList;

    @JoinColumn(name = "MainCategory", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Maincategory mainCategory;

    public Subcategory() {
    }

    public Subcategory(Integer id) {
        this.id = id;
    }

    public Subcategory(Integer id, String arabicLabel, String englishLabel) {
        this.id = id;
        this.arabicLabel = arabicLabel;
        this.englishLabel = englishLabel;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public Maincategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Maincategory mainCategory) {
        this.mainCategory = mainCategory;
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
        if (!(object instanceof Subcategory)) {
            return false;
        }
        Subcategory other = (Subcategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "arabicLabel='" + arabicLabel + '\'' +
                ", englishLabel='" + englishLabel + '\'' +
                ", enabled=" + enabled +
                ", topicList=" + topicList +
                ", mainCategory=" + mainCategory +
                '}';
    }
}
