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

/**
 * @author achah
 */
@Entity
@Table(name = "topicspermissions")
@XmlRootElement
@NamedStoredProcedureQueries(
        {
                @NamedStoredProcedureQuery(
                        name = "GenerateUserTopicsPermissions",
                        procedureName = "dbo.GenerateTopicUserPermissionMapping",
                        parameters = {
                                @StoredProcedureParameter(
                                        type = Integer.class,
                                        mode = ParameterMode.IN),
                                @StoredProcedureParameter(
                                        type = String.class,
                                        mode = ParameterMode.IN),
                                @StoredProcedureParameter(
                                        type = Integer.class,
                                        mode = ParameterMode.IN)
                        }),
                @NamedStoredProcedureQuery(
                        name = "GenerateGroupTopicsPermissions",
                        procedureName = "dbo.GenerateTopicGroupPermissionMapping",
                        parameters = {
                                @StoredProcedureParameter(
                                        type = Integer.class,
                                        mode = ParameterMode.IN),
                                @StoredProcedureParameter(
                                        type = String.class,
                                        mode = ParameterMode.IN),
                                @StoredProcedureParameter(
                                        type = Integer.class,
                                        mode = ParameterMode.IN)
                        })
                , @NamedStoredProcedureQuery(
                name = "GetUserTopicPermissions",
                procedureName = "dbo.GetUserTopicPermissions",
                resultClasses = {Topicspermissions.class},
                parameters = {
                        @StoredProcedureParameter(
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                type = Integer.class,
                                mode = ParameterMode.IN)
                })
                , @NamedStoredProcedureQuery(
                name = "GetUserTopics",
                procedureName = "dbo.GetUserTopics",
                resultClasses = {Topic.class},
                parameters = {
                        @StoredProcedureParameter(
                                type = String.class,
                                mode = ParameterMode.IN)
                }), @NamedStoredProcedureQuery(
                name = "GetUserMainCats",
                procedureName = "dbo.GetUserMainCats",
                resultClasses = {Maincategory.class},
                parameters = {
                        @StoredProcedureParameter(
                                type = String.class,
                                mode = ParameterMode.IN)
                }), @NamedStoredProcedureQuery(
                name = "GetUserSubCats",
                procedureName = "dbo.GetUserSubCats",
                resultClasses = {Subcategory.class},
                parameters = {
                        @StoredProcedureParameter(
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                type = Integer.class,
                                mode = ParameterMode.IN)
                }), @NamedStoredProcedureQuery(
                name = "GetUserTopicsBySubCat",
                procedureName = "dbo.GetUserTopicsBySubCat",
                resultClasses = {Topic.class},
                parameters = {
                        @StoredProcedureParameter(
                                type = String.class,
                                mode = ParameterMode.IN),
                        @StoredProcedureParameter(
                                type = Integer.class,
                                mode = ParameterMode.IN)
                })
        }

)

public class Topicspermissions extends BasicModelWithID {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "assigne")
    private Integer assigne;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "type")
    private String type;
    @Column(name = "admin")
    private boolean admin;
    @Column(name = "canCreate")
    private boolean canCreate;
    @Column(name = "canReopen")
    private boolean canReopen;
    @Column(name = "canRead")
    private boolean canRead;
    @Column(name = "canDelete")
    private boolean canDelete;
    @Column(name = "canReply")
    private boolean canReply;
    @Column(name = "canClose")
    private boolean canClose;
    @Column(name = "canResolve")
    private boolean canResolve;
    @Column(name = "canModify")
    private boolean canModify;
    @Column(name = "canRunReport")
    private boolean canRunReport;
    @Column(name = "canSubscribe")
    private boolean canSubscribe;
    @JoinColumn(name = "topicId", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Topic topicId;

    public Topicspermissions() {
    }

    public Topicspermissions(Long id) {
        this.id = id;
    }

    public Topicspermissions(Long id, Integer assigne, String type) {
        this.id = id;
        this.assigne = assigne;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssigne() {
        return assigne;
    }

    public void setAssigne(Integer assigne) {
        this.assigne = assigne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getCanCreate() {
        return canCreate;
    }

    public void setCanCreate(boolean canCreate) {
        this.canCreate = canCreate;
    }

    public boolean getCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean getCanReopen() {
        return canReopen;
    }

    public void setCanReopen(boolean canReopen) {
        this.canReopen = canReopen;
    }

    public boolean getCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public boolean getCanClose() {
        return canClose;
    }

    public void setCanClose(boolean canClose) {
        this.canClose = canClose;
    }

    public boolean getCanResolve() {
        return canResolve;
    }

    public void setCanResolve(boolean canResolve) {
        this.canResolve = canResolve;
    }

    public boolean getCanRunReport() {
        return canRunReport;
    }

    public void setCanRunReport(boolean canRunReport) {
        this.canRunReport = canRunReport;
    }

    public boolean getCanSubscribe() {
        return canSubscribe;
    }

    public void setCanSubscribe(boolean canSubscribe) {
        this.canSubscribe = canSubscribe;
    }

    public Topic getTopicId() {
        return topicId;
    }

    public void setTopicId(Topic topicId) {
        this.topicId = topicId;
    }

    public boolean getCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Topicspermissions)) {
            return false;
        }
        Topicspermissions other = (Topicspermissions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }


}
