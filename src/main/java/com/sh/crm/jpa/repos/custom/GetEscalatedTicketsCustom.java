package com.sh.crm.jpa.repos.custom;

import com.sh.crm.services.sla.EsclatedTickets;

import java.util.List;

public interface GetEscalatedTicketsCustom {
    List<EsclatedTickets> getEscalatedTickets();
}
