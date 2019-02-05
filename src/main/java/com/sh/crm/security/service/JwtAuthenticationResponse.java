package com.sh.crm.security.service;

import com.sh.crm.general.holders.TicketExtras;
import com.sh.crm.jpa.entities.Users;

public class JwtAuthenticationResponse {


    private final String token;
    private Users user;
    private TicketExtras ticketExtras;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public JwtAuthenticationResponse(String token, Users user) {
        super();
        this.token = token;
        this.user = user;
    }

    public JwtAuthenticationResponse(String token, Users user,
                                     TicketExtras ticketExtras) {
        super();
        this.token = token;
        this.user = user;
        this.ticketExtras = ticketExtras;
    }

    public String getToken() {
        return this.token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public TicketExtras getTicketExtras() {
        return ticketExtras;
    }

    public void setTicketExtras(TicketExtras ticketExtras) {
        this.ticketExtras = ticketExtras;
    }
}
