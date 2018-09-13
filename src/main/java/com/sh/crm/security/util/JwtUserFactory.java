package com.sh.crm.security.util;

import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Users;
import com.sh.crm.security.model.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Users user, List<Permissions> authList) {
        return new JwtUser(user.getId(), user.getUserID(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPassword(), mapToGrantedAuthorities(authList), user.getEnabled());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Permissions> authorities) {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toList());
    }
}
