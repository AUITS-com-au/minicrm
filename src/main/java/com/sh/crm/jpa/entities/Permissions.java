/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author achah
 */
@Entity
@Audited
@Table(name = "permissions")
@XmlRootElement
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "GetPermissions",
                procedureName = "dbo.GetUserPermissions",
                resultClasses = {Permissions.class},
                parameters = {
                        @StoredProcedureParameter(
                                type = String.class,
                                mode = ParameterMode.IN)
                })
})
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "Permission")
    private String permission;
    @Size(max = 50)
    @Column(name = "ModuleName")
    private String moduleName;
    @Size(max = 50)
    @Column(name = "GroupName")
    private String groupName;
    @Size(max = 150)
    @Column(name = "Description")
    private String description;

    public Permissions() {
    }

    public Permissions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Permissions)) {
            return false;
        }
        Permissions other = (Permissions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Permissions[ id=" + id + " ]";
    }

}
