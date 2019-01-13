package com.sh.crm.general.exceptions;

public class UserNotAllowedException extends GeneralException {
    public UserNotAllowedException(String errorMessage) {
        super( "100001", errorMessage );
    }


}
