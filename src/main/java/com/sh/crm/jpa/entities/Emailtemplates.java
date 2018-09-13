/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author achah
 */
@Entity
@Table(name = "emailtemplates")
@XmlRootElement
public class Emailtemplates extends BasicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TemplateID")
    private Integer templateID;
    @Size(max = 150)
    @Column(name = "TemplateName")
    private String templateName;
    @Size(max = 2147483647)
    @Column(name = "TemplateData")
    private String templateData;
    @Column(name = "enabled")
    private Boolean enabled;


    public Emailtemplates() {
    }

    public Emailtemplates(Integer templateID) {
        this.templateID = templateID;
    }

    public Integer getTemplateID() {
        return templateID;
    }

    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateID != null ? templateID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emailtemplates)) {
            return false;
        }
        Emailtemplates other = (Emailtemplates) object;
        if ((this.templateID == null && other.templateID != null) || (this.templateID != null && !this.templateID.equals(other.templateID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Emailtemplates[ templateID=" + templateID + " ]";
    }

}
