package com.sh.crm.security.util;

public class Utils {

    public static String setPrepaidMobileScheme(String mobile) {
        if (mobile != null) {
            if (mobile.startsWith("966")) {
                return mobile;
            }
            if (mobile.startsWith("05")) {
                mobile = mobile.substring(1);
                mobile = "966" + mobile;
            }
            if (mobile.startsWith("5")) {
                mobile = "966" + mobile;
            }
            if (mobile.startsWith("0966")) {
                return mobile.substring(1);
            }
            if (mobile.startsWith("00966")) {
                return mobile.substring(2);
            }

        }
        return mobile;
    }


    public static String getFormattedID(String IDNumber) {
        if (IDNumber == null || IDNumber.equals(""))
            return "";
        if (IDNumber.startsWith(Consts.PASSPORT.getValue()) || IDNumber.startsWith(Consts.NAT_ID.getValue())
                || IDNumber.startsWith(Consts.DIPLOMATIC_CARD.getValue())
                || IDNumber.startsWith(Consts.FAMILY_BOOK.getValue())
                || IDNumber.startsWith(Consts.CORPORATES.getValue())
                || IDNumber.startsWith(Consts.REGISTERATION_BOOK.getValue())
                || IDNumber.startsWith(Consts.GCC_PASSPORT.getValue()) || IDNumber.startsWith(Consts.IQAMA.getValue())
                || IDNumber.startsWith(Consts.HAFEEZA.getValue())
                || IDNumber.startsWith(Consts.BUSINESS_LICENSE.getValue()))
            return IDNumber;
        if (IDNumber.startsWith("1") && IDNumber.length() == 10) {
            IDNumber = Consts.NAT_ID.getValue() + IDNumber;
        } else if (IDNumber.startsWith("2") && IDNumber.length() == 10) {
            IDNumber = Consts.IQAMA.getValue() + IDNumber;
        } else {
            IDNumber = Consts.BUSINESS_LICENSE.getValue() + IDNumber;
        }
        return IDNumber;
    }
}
