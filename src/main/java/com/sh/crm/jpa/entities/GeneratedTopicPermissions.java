package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "generatedTopicPermissions")
@XmlRootElement
public class GeneratedTopicPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "TopicID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Topic topic;
    @Size(max = 50)
    @Column(name = "UserName")
    private String userName;
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Size(max = 50)
    @Column(name = "CreatedBy")
    private String createdBy;
    @Column(name = "InheritedFrom")
    private String inheritedFrom;
    @Column(name = "admin")
    private Boolean admin;
    @Column(name = "canCreate")
    private Boolean canCreate;
    @Column(name = "canReopen")
    private Boolean canReopen;
    @Column(name = "canRead")
    private Boolean canRead;
    @Column(name = "canDelete")
    private Boolean canDelete;
    @Column(name = "canReply")
    private Boolean canReply;
    @Column(name = "canClose")
    private Boolean canClose;
    @Column(name = "canResolve")
    private Boolean canResolve;
    @Column(name = "canChgDpt")
    private Boolean canChgDpt;
    @Column(name = "canAssign")
    private Boolean canAssign;
    @Column(name = "canModify")
    private Boolean canModify;
    @Column(name = "canRunReport")
    private Boolean canRunReport;
    @Column(name = "canSubscribe")
    private Boolean canSubscribe;
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Users userID;

    public GeneratedTopicPermissions() {
    }

    public GeneratedTopicPermissions(Long id) {
        this.id = id;
    }

    public GeneratedTopicPermissions(Long id, Topic topicID) {
        this.id = id;
        this.topic = topicID;
    }

    public Boolean getCanAssign() {
        return canAssign;
    }

    public void setCanAssign(Boolean canAssign) {
        this.canAssign = canAssign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getCanCreate() {
        return canCreate;
    }

    public void setCanCreate(Boolean canCreate) {
        this.canCreate = canCreate;
    }

    public Boolean getCanReopen() {
        return canReopen;
    }

    public void setCanReopen(Boolean canReopen) {
        this.canReopen = canReopen;
    }

    public Boolean getCanRead() {
        return canRead;
    }

    public void setCanRead(Boolean canRead) {
        this.canRead = canRead;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Boolean getCanReply() {
        return canReply;
    }

    public void setCanReply(Boolean canReply) {
        this.canReply = canReply;
    }

    public Boolean getCanClose() {
        return canClose;
    }

    public void setCanClose(Boolean canClose) {
        this.canClose = canClose;
    }

    public Boolean getCanResolve() {
        return canResolve;
    }

    public void setCanResolve(Boolean canResolve) {
        this.canResolve = canResolve;
    }

    public Boolean getCanModify() {
        return canModify;
    }

    public void setCanModify(Boolean canModify) {
        this.canModify = canModify;
    }

    public Boolean getCanRunReport() {
        return canRunReport;
    }

    public void setCanRunReport(Boolean canRunReport) {
        this.canRunReport = canRunReport;
    }

    public Boolean getCanSubscribe() {
        return canSubscribe;
    }

    public void setCanSubscribe(Boolean canSubscribe) {
        this.canSubscribe = canSubscribe;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public String getInheritedFrom() {
        return inheritedFrom;
    }

    public void setInheritedFrom(String inheritedFrom) {
        this.inheritedFrom = inheritedFrom;
    }

    public Boolean getCanChgDpt() {
        return canChgDpt;
    }

    public void setCanChgDpt(Boolean canChgDpt) {
        this.canChgDpt = canChgDpt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GeneratedTopicPermissions)) {
            return false;
        }
        GeneratedTopicPermissions other = (GeneratedTopicPermissions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.GeneratedTopicPermissions[ id=" + id + " ]";
    }
}
