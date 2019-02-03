
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@Entity
@Table(name = "escalationhistory")
@XmlRootElement
public class Escalationhistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESCDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date escDateTime;
    @JoinColumn(name = "topicSLA", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Topicsla topicSLA;
    @JoinColumn(name = "TicketID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Ticket ticketID;


    public Escalationhistory() {
    }

    public Escalationhistory(Long id) {
        this.id = id;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEscDateTime() {
        return escDateTime;
    }

    public void setEscDateTime(Date escDateTime) {
        this.escDateTime = escDateTime;
    }

    public Topicsla getTopicSLA() {
        return topicSLA;
    }

    public void setTopicSLA(Topicsla topicSLA) {
        this.topicSLA = topicSLA;
    }

    public Ticket getTicketID() {
        return ticketID;
    }

    public void setTicketID(Ticket ticketID) {
        this.ticketID = ticketID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Escalationhistory)) {
            return false;
        }
        Escalationhistory other = (Escalationhistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }


}
