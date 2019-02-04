package com.sh.crm.security.controller;


import com.sh.crm.general.holders.TicketExtras;
import com.sh.crm.jpa.entities.Permissions;
import com.sh.crm.jpa.entities.Users;
import com.sh.crm.jpa.repos.users.UserPreferencesRepo;
import com.sh.crm.jpa.repos.users.UsersRepos;
import com.sh.crm.rest.controllers.ticket.TicketExtrasController;
import com.sh.crm.security.model.JwtAuthenticationRequest;
import com.sh.crm.security.service.JwtAuthenticationResponse;
import com.sh.crm.security.service.JwtUserDetailsServiceImpl;
import com.sh.crm.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AuthenticationRestController {

    private static final Logger log = LoggerFactory.getLogger( AuthenticationRestController.class );
    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsersRepos usersRepos;
    @Autowired
    private UserPreferencesRepo userPreferencesRepo;
    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;
    @Autowired
    private TicketExtrasController ticketExtrasController;

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        log.debug( " >>>>>>>> Authentication Request <<<<<<<<<<< " );

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication( authentication );

        // Reload password post-security so we can generate token
        //final JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final Users user = usersRepos.findByUserID( authenticationRequest.getUsername() );

        final List<Permissions> authorities = userDetailsService.getUserAuthorities( user.getUserID() );
        user.setAuthorities( authorities );
        final String token = jwtTokenUtil.generateToken( user );

        user.setPassword( null );
        user.setLoginAttempts( null );

        TicketExtras ticketExtras = ticketExtrasController.getTicketExtras();

        // Return the token
        return ResponseEntity.ok( new JwtAuthenticationResponse( token, user, ticketExtras ) );
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        log.debug( " >>>>>>>> Token refresh Request <<<<<<<<<<< " );

        String token = request.getHeader( tokenHeader );
        if (token != null && token.startsWith( "Bearer " )) {
            token = token.substring( 7 );
        }
        String username = jwtTokenUtil.getUsernameFromToken( token );
        log.info( "Username found: " + username );
        //JwtUser user = (JwtUser) userDetailsService.loadUserByUsername( username );
        Users user = usersRepos.findByUserID( username );
        if (jwtTokenUtil.canTokenBeRefreshed( token, java.util.Calendar.getInstance().getTime() )) {
            String refreshedToken = jwtTokenUtil.refreshToken( token );
            return ResponseEntity.ok( new JwtAuthenticationResponse( refreshedToken, user ) );
        } else {
            return ResponseEntity.badRequest().body( null );
        }
    }

}
