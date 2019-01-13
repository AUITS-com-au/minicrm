package com.sh.crm.general.exceptions;

import com.sh.crm.general.Errors;

public class GeneralException extends BasicException {

    public GeneralException(String errorCode, String message) {
        super( errorCode, message );
    }

    public GeneralException(String message, Throwable e) {
        super( e );
        super.errorCode = message;
    }

    public GeneralException() {

    }

    public GeneralException(String message) {
        super( "", message );
    }

    public GeneralException(Errors error) {
        super( error.getCode(), error.getDesc() );
    }

    public GeneralException(Errors error, Throwable e) {
        super( error.getCode(), error.getDesc() + "\n Throwable " + e.getMessage() );
    }

    public GeneralException(Errors error, String msg) {
        super( error.getCode(), error.getDesc() + "\n Message " + msg );
    }
}
