package com.sh.crm.jpa.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

//@Configuration
public class HibernateSessionBean {
    @Autowired
    private EntityManagerFactory em;

    @Bean
    public SessionFactory getSessionFactory() {
        if (em.unwrap( SessionFactory.class ) == null) {
            throw new NullPointerException( "factory is not a hibernate factory" );
        }
        return em.unwrap( SessionFactory.class );
    }
}
