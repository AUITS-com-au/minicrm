package com.sh.crm.security.service;

import com.sh.crm.security.model.JwtUser;
 ;

import java.io.Serializable;


public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String token;

    private JwtUser user;


    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }


    public JwtAuthenticationResponse(String token, JwtUser user) {
        super();
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public JwtUser getUser() {
        return user;
    }

    public void setUser(JwtUser user) {
        this.user = user;
    }

}
