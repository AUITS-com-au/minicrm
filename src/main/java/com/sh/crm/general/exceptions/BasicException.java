package com.sh.crm.general.exceptions;

public abstract class BasicException extends Exception {

    protected String errorCode;

    public BasicException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BasicException() {
        super("");
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
