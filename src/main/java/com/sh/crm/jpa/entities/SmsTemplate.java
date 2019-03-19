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
@Table(name = "smsTemplate")
@XmlRootElement
public class SmsTemplate extends BasicModelWithIDInt {

    @Column(name = "Enabled")
    private Boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TemplateName")
    private String templateName;

    @Size(max = 700)
    @Column(name = "TemplateText")
    private String templateText;

    public SmsTemplate() {
    }

    public SmsTemplate(Integer id) {
        this.id = id;
    }

    public SmsTemplate(Integer id, String templateName) {
        this.id = id;
        this.templateName = templateName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
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
        if (!(object instanceof SmsTemplate)) {
            return false;
        }
        SmsTemplate other = (SmsTemplate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.SmsTemplate[ id=" + id + " ]";
    }

}
