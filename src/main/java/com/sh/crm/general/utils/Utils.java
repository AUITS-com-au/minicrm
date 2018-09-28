package com.sh.crm.general.utils;

import com.sh.crm.jpa.entities.Ticketactions;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.security.model.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger( Utils.class );

    public static Optional<JwtUser> currentUser() {

        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();

        if (auth != null) {

            Object principal = auth.getPrincipal();

            if (principal instanceof JwtUser)
                return Optional.of( (JwtUser) principal );
        }

        return Optional.empty();
    }

    public static String getUserName() {
        return currentUser().get().getUsername();
    }

    public static Calendar getDateShiftedMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.MONTH, months );
        return cal;
    }

    public static String padSeq(Long seq, int length) {
        String seqStr = seq.toString();
        if (seqStr.length() > length)
            return null;
        if (seqStr.length() == length)
            return seqStr;
        StringBuilder sb = new StringBuilder();
        int accumlatedValue = length - seqStr.length();
        for (int j = 0; j < accumlatedValue; j++) {
            sb.append( "0" );
        }
        sb.append( seqStr );
        return sb.toString();
    }

    public static String getLikeClauseValue(String value) {
        if (value != null) {
            value = "%" + value + "%";
        }

        return value;
    }

    public static boolean isAllowedPermission(Set<Topicspermissions> topicspermissions, Ticketactions ticketactions) {
        /**
         * Actions mapping must be same as database
         * 1 resolved
         * 2 close
         * 3 reopen
         * 4 open
         * 5 On Progress
         * 6 hold
         */
        if (topicspermissions == null || topicspermissions.isEmpty()) {
            log.debug( "topics permissions are null, return false" );
            return false;
        }
        String operation = getOperationFromAction( ticketactions );


        if (operation == null) {
            log.info( "cannot map ticket action {} with proper operation", ticketactions.toString() );
            return false;
        } else {
            if (log.isDebugEnabled())
                log.debug( "ticket action {} has been translated to ticket operation {}", ticketactions, operation );
        }
        return isAllowedPermission( topicspermissions, operation );
    }

    public static String getOperationFromAction(Ticketactions ticketactions) {
        String operation = null;
        switch (ticketactions.getActionID()) {
            case 1:
                operation = TicketOperation.RESOLVE;
                break;
            case 2:
                operation = TicketOperation.CLOSE;
                break;
            case 3:
                operation = TicketOperation.REOPEN;
                break;
            case 4:
                operation = TicketOperation.CREATE;
                break;
            case 5:
                operation = TicketOperation.REPLY;
                break;
            case 6:
                operation = TicketOperation.REPLY;
                break;
            case 7:
                operation = TicketOperation.MODIFYINFO;
                break;
            default:
                operation = null;
                break;
        }
        return operation;
    }

    private static boolean isAllowedPermission(Set<Topicspermissions> topicspermissions, String operation) {

        switch (operation) {
            case TicketOperation.CLOSE:
                return isAllowedClose( topicspermissions );
            case TicketOperation.CREATE:
                return isAllowedCreate( topicspermissions );
            case TicketOperation.DELETE:
                return isAllowedDelete( topicspermissions );
            case TicketOperation.READ:
                return isAllowedRead( topicspermissions );
            case TicketOperation.REPLY:
                return isAllowedReply( topicspermissions );
            case TicketOperation.REPORT:
                return isAllowedRunReport( topicspermissions );
            case TicketOperation.RESOLVE:
                return isAllowedResolve( topicspermissions );
            case TicketOperation.SUBSCRIBE:
                return isAllowedSubscribe( topicspermissions );
            case TicketOperation.REOPEN:
                return isAllowedReOpen( topicspermissions );
            case TicketOperation.MODIFYINFO:
                return isAllowedModify( topicspermissions );
            default:
                return false;
        }
    }


    private static boolean isAllowedRead(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanRead()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedModify(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanModify()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedReply(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanReply()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedReOpen(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanReopen()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedCreate(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanCreate()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedDelete(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanDelete()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedClose(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanClose()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedResolve(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanResolve()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedRunReport(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanRunReport()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowedSubscribe(Set<Topicspermissions> tps) {
        for (Topicspermissions tp : tps) {
            if (tp.getCanSubscribe()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.print( padSeq( 1l, 6 ) );
    }
}