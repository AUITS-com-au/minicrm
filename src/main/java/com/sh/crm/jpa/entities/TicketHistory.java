package com.sh.crm.jpa.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author ahmed shaheen
 */
@Entity
@Table(name = "TicketHistory")
@XmlRootElement
public class TicketHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TicketID")
    private long ticketID;

    @Size(max = 50)
    @Column(name = "CreatedBy")
    private String createdBy;
    @JoinColumn(name = "ActionID", referencedColumnName = "actionID")
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Ticketactions actionID;
    @Column(name = "OldStatus")
    private Integer oldStatus;
    @Column(name = "NewStatus")
    private Integer newStatus;
    @JoinColumn(name = "OldTopic", referencedColumnName = "ID")
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Topic oldTopic;
    @JoinColumn(name = "NewTopic", referencedColumnName = "ID")
    @ManyToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Topic newTopic;
    @Column(name = "OldAssigne")
    private String oldAssigne;
    @Column(name = "NewAssigne")
    private String newAssigne;
    @Column(name = "CreationDate")
    protected Date creationDate;

    @Column(name = "DataID")
    private Long dataID;

    @Column(name = "EscalationHisID")
    private Long escalationHisID;

    public TicketHistory() {
    }

    public TicketHistory(Long id) {
        this.id = id;
    }

    public TicketHistory(long ticketID) {

        this.ticketID = ticketID;

    }

    public long getTicketID() {
        return ticketID;
    }

    public void setTicketID(long ticketID) {
        this.ticketID = ticketID;
    }

    public Ticketactions getActionID() {
        return actionID;
    }

    public void setActionID(Ticketactions actionID) {
        this.actionID = actionID;
    }


    public Topic getOldTopic() {
        return oldTopic;
    }

    public void setOldTopic(Topic oldTopic) {
        this.oldTopic = oldTopic;
    }

    public Topic getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(Topic newTopic) {
        this.newTopic = newTopic;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Integer getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Integer newStatus) {
        this.newStatus = newStatus;
    }

    public String getOldAssigne() {
        return oldAssigne;
    }

    public void setOldAssigne(String oldAssigne) {
        this.oldAssigne = oldAssigne;
    }

    public String getNewAssigne() {
        return newAssigne;
    }

    public void setNewAssigne(String newAssigne) {
        this.newAssigne = newAssigne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getDataID() {
        return dataID;
    }

    public void setDataID(Long dataID) {
        this.dataID = dataID;
    }

    public Long getEscalationHisID() {
        return escalationHisID;
    }

    public void setEscalationHisID(Long escalationHisID) {
        this.escalationHisID = escalationHisID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TicketHistory)) {
            return false;
        }
        TicketHistory other = (TicketHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }


}
