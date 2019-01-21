package com.sh.crm.security.service;

import com.sh.crm.jpa.entities.Users;
import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String token;
    private Users user;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public JwtAuthenticationResponse(String token, Users user) {
        super();
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
