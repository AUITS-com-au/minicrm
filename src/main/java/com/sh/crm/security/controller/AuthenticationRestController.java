package com.sh.crm.security.controller;


import com.sh.crm.security.model.JwtAuthenticationRequest;
import com.sh.crm.security.model.JwtUser;
import com.sh.crm.security.service.JwtAuthenticationResponse;
import com.sh.crm.security.service.JwtUserDetailsServiceImpl;
import com.sh.crm.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationRestController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationRestController.class);
    @Value("${jwt.header}")
    private String tokenHeader;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        log.debug(" >>>>>>>> Authentication Request <<<<<<<<<<< ");

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken( userDetails );

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, userDetails));
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        log.debug(" >>>>>>>> Token refresh Request <<<<<<<<<<< ");

        String token = request.getHeader(tokenHeader);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        log.info("Username found: " + username);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, java.util.Calendar.getInstance().getTime())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, user));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
