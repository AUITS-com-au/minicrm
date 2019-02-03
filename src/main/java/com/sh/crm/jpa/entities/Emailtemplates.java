
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


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
    @Size(max = 500)
    @Column(name = "TemplateTitle")
    private String templateTitle;
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

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateID != null ? templateID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Emailtemplates)) {
            return false;
        }
        Emailtemplates other = (Emailtemplates) object;
        if ((this.templateID == null && other.templateID != null) || (this.templateID != null && !this.templateID.equals( other.templateID ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Emailtemplates{" +
                "templateID=" + templateID +
                ", templateName='" + templateName + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
