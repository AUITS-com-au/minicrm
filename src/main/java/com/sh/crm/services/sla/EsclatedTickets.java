package com.sh.crm.services.sla;

import java.math.BigInteger;

public class EsclatedTickets {
    private BigInteger ticketID;
    private Integer topicSLA;


    public EsclatedTickets() {

    }

    public EsclatedTickets(BigInteger ticketID, Integer topicSLA) {
        this.ticketID = ticketID;
        this.topicSLA = topicSLA;
    }

    public BigInteger getTicketID() {
        return ticketID;
    }

    public void setTicketID(BigInteger ticketID) {
        this.ticketID = ticketID;
    }

    public Integer getTopicSLA() {
        return topicSLA;
    }

    public void setTopicSLA(Integer topicSLA) {
        this.topicSLA = topicSLA;
    }

    @Override
    public String toString() {
        return "EsclatedTickets{" +
                "ticketID=" + ticketID +
                ", topicSLA=" + topicSLA +
                '}';
    }
}
