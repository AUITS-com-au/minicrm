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


}
