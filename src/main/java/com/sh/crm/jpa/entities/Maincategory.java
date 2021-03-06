package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@Table(name = "maincategory")
@XmlRootElement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Maincategory extends BasicModelWithIDInt {
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
    @Column(name = "Configuration")
    private String configuration;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private List<Maincatholidays> maincatholidaysList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainCategory", fetch = FetchType.LAZY)
    private List<Subcategory> subcategoryList;

    public Maincategory() {
    }

    public Maincategory(Integer id) {
        this.id = id;
    }

    public Maincategory(Integer id, String arabicLabel, String englishLabel) {
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


    @JsonProperty("configuration")
    public JsonNode getConfigurationNode() {
        if (configuration != null && !configuration.equalsIgnoreCase( "" ))
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode object = mapper.readTree( configuration );
                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @JsonProperty("configuration")
    public void setConfigurationNode(JsonNode node) {
        if (node != null)
            try {
                ObjectMapper mapper = new ObjectMapper();
                String object = mapper.writeValueAsString( node );
                setConfiguration( object );
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    public String getConfiguration() {
        return this.configuration;
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

    @XmlTransient
    public List<Maincatholidays> getMaincatholidaysList() {
        return maincatholidaysList;
    }

    public void setMaincatholidaysList(List<Maincatholidays> maincatholidaysList) {
        this.maincatholidaysList = maincatholidaysList;
    }

    @XmlTransient
    public List<Subcategory> getSubcategoryList() {
        return subcategoryList;
    }

    public void setSubcategoryList(List<Subcategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
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
        if (!(object instanceof Maincategory)) {
            return false;
        }
        Maincategory other = (Maincategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Maincategory[ id=" + id + " ]";
    }

}
