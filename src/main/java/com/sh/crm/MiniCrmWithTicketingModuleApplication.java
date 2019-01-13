package com.sh.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
public class MiniCrmWithTicketingModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run( MiniCrmWithTicketingModuleApplication.class, args );
    }
}
