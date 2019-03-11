package com.sh.crm.email.enums;

public interface EmailStatus {
    public static final int SUCCESSFULL = 0;
    public static final int FAILED = 1;
    public static final int DONTSEND = 1000;
    public static final int MAXTRIES = 1001;
}
