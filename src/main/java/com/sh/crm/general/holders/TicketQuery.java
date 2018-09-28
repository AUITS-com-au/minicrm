package com.sh.crm.general.holders;

import java.util.Date;
import java.util.List;

public class TicketQuery {
    private List<Long> tickets;
    private List<Integer> topics;
    private List<Integer> originalTopics;
    private Date startDate;
    private Date endDate;
    private Boolean myAssigned;
    private Boolean closed;
    private Boolean open;
    private Integer status;
    private Integer channel;
    private Integer type;
    private Integer lastSLA;

    public TicketQuery() {

    }

    public List<Long> getTickets() {
        return tickets;
    }

    public void setTickets(List<Long> tickets) {
        this.tickets = tickets;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }

    public List<Integer> getOriginalTopics() {
        return originalTopics;
    }

    public void setOriginalTopics(List<Integer> originalTopics) {
        this.originalTopics = originalTopics;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getMyAssigned() {
        return myAssigned;
    }

    public void setMyAssigned(Boolean myAssigned) {
        this.myAssigned = myAssigned;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLastSLA() {
        return lastSLA;
    }

    public void setLastSLA(Integer lastSLA) {
        this.lastSLA = lastSLA;
    }
}
