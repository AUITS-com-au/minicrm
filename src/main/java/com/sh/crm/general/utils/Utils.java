package com.sh.crm.general.utils;

import com.sh.crm.security.model.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;
import java.util.Optional;

public class Utils {
    public static Optional<JwtUser> currentUser() {

        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();

        if (auth != null) {

            Object principal = auth.getPrincipal();

            if (principal instanceof JwtUser)
                return Optional.of((JwtUser) principal);
        }

        return Optional.empty();
    }

    public static String getUserName() {
        return currentUser().get().getUsername();
    }

    public static Calendar getDateShiftedMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        return cal;
    }

    public static String getLikeClauseValue(String value) {
        if (value != null) {
            value = "%" + value + "%";
        }

        return value;
    }
}