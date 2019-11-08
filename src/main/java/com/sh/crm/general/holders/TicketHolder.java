package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.CustomerAccounts;
import com.sh.crm.jpa.entities.Ticket;
import com.sh.crm.jpa.entities.TicketExtData;
import com.sh.crm.jpa.entities.Ticketdata;

import java.util.List;

public class TicketHolder {
    private Ticket ticket;
    private CustomerAccounts customerAccount;
    private List<TicketExtData> extDataList;
    private Ticketdata ticketdata;
    private Long lockID;
    private Integer actionID;
    private Integer newTopic;
    private List<Long> ticketList;
    private String targetUser;

    private List<Long> attachments;


    public TicketHolder() {
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public CustomerAccounts getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccounts customerAccount) {
        this.customerAccount = customerAccount;
    }

    public List<TicketExtData> getExtDataList() {
        return extDataList;
    }

    public void setExtDataList(List<TicketExtData> extDataList) {
        this.extDataList = extDataList;
    }

    public Ticketdata getTicketdata() {
        return ticketdata;
    }

    public void setTicketdata(Ticketdata ticketdata) {
        this.ticketdata = ticketdata;
    }

    public Long getLockID() {
        return lockID;
    }

    public void setLockID(Long lockID) {
        this.lockID = lockID;
    }

    public Integer getActionID() {
        return actionID;
    }

    public void setActionID(Integer actionID) {
        this.actionID = actionID;
    }

    public List<Long> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Long> ticketList) {
        this.ticketList = ticketList;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public Integer getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(Integer newTopic) {
        this.newTopic = newTopic;
    }

    public List<Long> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Long> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "TicketHolder{" +
                "ticket=" + ticket +
                ", customerAccount=" + customerAccount +
                ", extDataList=" + extDataList +
                ", ticketdata=" + ticketdata +
                ", lockID=" + lockID +
                ", actionID=" + actionID +
                ", newTopic=" + newTopic +
                ", ticketList=" + ticketList +
                ", targetUser='" + targetUser + '\'' +
                '}';
    }
}
