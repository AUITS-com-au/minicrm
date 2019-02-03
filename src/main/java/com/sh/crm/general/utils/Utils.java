package com.sh.crm.general.utils;

import com.sh.crm.jpa.entities.CustomerAccounts;
import com.sh.crm.jpa.entities.Ticketactions;
import com.sh.crm.security.model.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Optional;

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
/**
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
    /**
     * if (topicspermissions == null || topicspermissions.isEmpty()) {
     * log.debug( "topics permissions are null, return false" );
     * return false;
     * }
     * String operation = getOperationFromAction( ticketactions );
     * <p>
     * <p>
     * if (operation == null) {
     * log.info( "cannot map ticket action {} with proper operation", ticketactions.toString() );
     * return false;
     * } else {
     * if (log.isDebugEnabled())
     * log.debug( "ticket action {} has been translated to ticket operation {}", ticketactions, operation );
     * }
     * return isAllowedPermission( topicspermissions, operation );
     * }
     */


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
            case 6:
            case 12:
                operation = TicketOperation.REPLY;
                break;
            case 7:
                operation = TicketOperation.MODIFYINFO;
                break;
            case 8:
                operation = TicketOperation.CHGDPT;
                break;
            case 9:
                operation = TicketOperation.READ;
                break;
            case 11:
                operation = TicketOperation.ASSIGN;
                break;
            case 13:
                operation = TicketOperation.ESC;
                break;
            default:
                operation = null;
                break;
        }
        return operation;
    }

    public static String getOperationFromAction(Integer ticketactions) {
        return getOperationFromAction( new Ticketactions( ticketactions ) );
    }

    public static void copyAccount(CustomerAccounts copyFrom, CustomerAccounts copyTo) {
        if (copyFrom.getBranchName() != null)
            copyTo.setBranchName( copyFrom.getBranchName() );
        if (copyFrom.getCustomerNameAR() != null)
            copyTo.setCustomerNameAR( copyFrom.getCustomerNameAR() );
        if (copyFrom.getCustomerNameEn() != null)
            copyTo.setCustomerNameEn( copyFrom.getCustomerNameEn() );
        if (copyFrom.getEmail() != null)
            copyTo.setEmail( copyFrom.getEmail() );
        if (copyFrom.getMobile() != null)
            copyTo.setMobile( copyFrom.getMobile() );
        if (copyFrom.getSegment() != null)
            copyTo.setSegment( copyFrom.getSegment() );

    }

    public static void main(String[] args) {
        System.out.print( padSeq( 1l, 6 ) );
    }
}