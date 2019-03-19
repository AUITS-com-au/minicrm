package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TicketAttachments")
@XmlRootElement
public class TicketAttachments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DataID")
    private Long dataID;
    @Column(name = "AttachmentID")
    private Long attachmentID;
    @Column(name = "TicketID")
    private Long ticketID;

    public TicketAttachments() {
    }

    public TicketAttachments(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataID() {
        return dataID;
    }

    public void setDataID(Long dataID) {
        this.dataID = dataID;
    }

    public Long getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Long attachmentID) {
        this.attachmentID = attachmentID;
    }

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
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

        if (!(object instanceof TicketAttachments)) {
            return false;
        }
        TicketAttachments other = (TicketAttachments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }


}
