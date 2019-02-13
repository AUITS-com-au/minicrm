
package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "ticketdata")
@XmlRootElement
public class Ticketdata extends BasicModelWithID {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Ticketactions.class)
    @JoinColumn(name = "actionID")
    private Ticketactions actionID;
    @Size(max = 400)
    @Column(name = "Title")
    private String title;
    @Size(max = 2147483647)
    @Column(name = "TicketData")
    private String ticketData;
    @Size(max = 2147483647)
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Hidden")
    private Boolean hidden;
    /*  @Column(name = "OldStatus")
      private Integer oldStatus;
      @Column(name = "NewStatus")
      private Integer newStatus;
      @Column(name = "OldTopic")
      private Integer oldTopic;
      @Column(name = "NewTopic")
      private Integer newTopic;*/
    @Column(name = "NotificationID")
    private BigInteger notificationID;
    @Column(name = "Notify")
    private Boolean notify;
    @Column(name = "NotfiyStatus")
    private Integer notfiyStatus;
    @JsonIgnore
    @JoinColumn(name = "TicketID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ticketID;
    @Transient
    private List<Long> attachmentsList;


    public Ticketdata() {
    }

    public Ticketdata(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticketactions getActionID() {
        return actionID;
    }

    public void setActionID(Ticketactions actionID) {
        this.actionID = actionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicketData() {
        return ticketData;
    }

    public void setTicketData(String ticketData) {
        this.ticketData = ticketData;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }


    public BigInteger getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(BigInteger notificationID) {
        this.notificationID = notificationID;
    }

    public Boolean getNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public Integer getNotfiyStatus() {
        return notfiyStatus;
    }

    public void setNotfiyStatus(Integer notfiyStatus) {
        this.notfiyStatus = notfiyStatus;
    }

    public Ticket getTicketID() {
        return ticketID;
    }

    public void setTicketID(Ticket ticketID) {
        this.ticketID = ticketID;
    }

    public List<Long> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<Long> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Ticketdata)) {
            return false;
        }
        Ticketdata other = (Ticketdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticketdata{" +
                "actionID=" + actionID +
                ", title='" + title + '\'' +
                ", ticketData='" + ticketData + '\'' +
                ", notes='" + notes + '\'' +
                ", hidden=" + hidden +
                ", notificationID=" + notificationID +
                ", notify=" + notify +
                ", notfiyStatus=" + notfiyStatus +
                ", ticketID=" + ticketID +
                ", id=" + id +
                ", createdBy='" + createdBy + '\'' +
                ", modificationDate=" + modificationDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
