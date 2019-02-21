package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.SourceChannel;
import com.sh.crm.jpa.entities.Status;
import com.sh.crm.jpa.entities.Ticketactions;
import com.sh.crm.jpa.entities.Tickettypes;

import java.util.List;

public class TicketExtras {
    private List<Ticketactions> ticketactionsList;
    private List<Status> ticketStatusList;
    private List<Tickettypes> tickettypesList;
    private List<TicketPriorityHolder> ticketPriorityList;
    private List<SourceChannel> channelsList;

    public TicketExtras() {

    }

    public List<Ticketactions> getTicketactionsList() {
        return ticketactionsList;
    }

    public void setTicketactionsList(List<Ticketactions> ticketactionsList) {
        this.ticketactionsList = ticketactionsList;
    }

    public List<Status> getTicketStatusList() {
        return ticketStatusList;
    }

    public void setTicketStatusList(List<Status> ticketStatusList) {
        this.ticketStatusList = ticketStatusList;
    }

    public List<Tickettypes> getTickettypesList() {
        return tickettypesList;
    }

    public void setTickettypesList(List<Tickettypes> tickettypesList) {
        this.tickettypesList = tickettypesList;
    }

    public List<TicketPriorityHolder> getTicketPriorityList() {
        return ticketPriorityList;
    }

    public List<SourceChannel> getChannelsList() {
        return channelsList;
    }

    public void setChannelsList(List<SourceChannel> channelsList) {
        this.channelsList = channelsList;
    }

    public void setTicketPriorityList(List<TicketPriorityHolder> ticketPriorityList) {
        this.ticketPriorityList = ticketPriorityList;
    }
}
