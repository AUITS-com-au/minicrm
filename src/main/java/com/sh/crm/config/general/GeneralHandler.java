package com.sh.crm.config.general;


import com.sh.crm.general.exceptions.BasicException;
import com.sh.crm.general.exceptions.GeneralException;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class GeneralHandler {

    @ExceptionHandler(value = {IOException.class, SQLException.class})
    public ResponseEntity<ResponseCode> ExceptionHandler(Exception ex) {
        ResponseCode responseCode = new ResponseCode("-1", "General Exception Occurred Please  Contact Your Admin");
        return new ResponseEntity<ResponseCode>(responseCode, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {GeneralException.class, BasicException.class})
    public ResponseEntity<ResponseCode> ExceptionHandler(BasicException ex) {
        ResponseCode responseCode = new ResponseCode(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<ResponseCode>(responseCode, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseCode> ExceptionHandler(BadCredentialsException ex) {
        ResponseCode responseCode = new ResponseCode("-2", "Invalid Username or Password");
        return new ResponseEntity<ResponseCode>(responseCode, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseCode> ExceptionHandler(ResourceNotFoundException ex) {
        ResponseCode responseCode = new ResponseCode("-3", "Service Not Found");
        return new ResponseEntity<ResponseCode>(responseCode, HttpStatus.BAD_REQUEST);

    }

}
