package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.repos.custom.GetEscalatedTicketsCustom;
import com.sh.crm.services.sla.EsclatedTickets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SlaRepoImpl implements GetEscalatedTicketsCustom {
    private static final Logger logger = LoggerFactory.getLogger( SlaRepoImpl.class );
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EsclatedTickets> getEscalatedTickets() {
        List<EsclatedTickets> result = null;
        try {
            StoredProcedureQuery spQ = em.createNamedStoredProcedureQuery( "GetEscalatedTickets" );
            List<Object[]> rs = spQ.getResultList();
            result = new ArrayList<>();
            if (rs != null && !rs.isEmpty()) {
                for (Object[] tuple : rs) {
                    result.add( new EsclatedTickets( (BigInteger) tuple[0], (Integer) tuple[1] ) );
                }
            }
        } catch (Exception e) {
            logger.error( "Error Getting Esclated Tickets {}", e );
            LoggingUtils.logStackTrace( logger, e, "error" );
        }
        return result;
    }
}
