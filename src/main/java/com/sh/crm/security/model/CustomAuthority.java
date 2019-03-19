package com.sh.crm.security.model;

import org.springframework.security.core.GrantedAuthority;


public class CustomAuthority implements GrantedAuthority {
    String authority;

    public CustomAuthority() {

    }

    public CustomAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
