package com.sh.crm;

import com.sh.crm.jpa.repos.notifications.EmailTemplatesRepo;
import com.sh.crm.jpa.repos.tickets.TicketsRepo;
import com.sh.crm.services.notification.Config;
import com.sh.crm.services.notification.consumer.NotificationConsumer;
import com.sh.crm.services.notification.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import reactor.bus.EventBus;

import static reactor.bus.selector.Selectors.$;

@Configuration
@Import(Config.class)
public class StartServiceBusCommandRunner implements CommandLineRunner {

    @Autowired
    private EventBus eventBus;
    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private EmailTemplatesRepo emailTemplatesRepo;
    @Autowired
    private NotificationConsumer notificationConsumer;
    @Autowired
    private TicketsRepo ticketsRepo;

    @Value("${mw.ssl.key-store}")
    private String keyStore;

    @Value("${mw.ssl.key-store-password}")
    private String keyStorePassword;

    @Override
    public void run(String... args) throws Exception {
        setSystemVariable();
        eventBus.on( $( "NC" ), notificationConsumer );

        // notificationService.formatTemplate( 1, 19011500016l, "" );
        //  Ticket ticket = ticketsRepo.findById( Long.parseLong( "18121600001" ) ).orElse( null );

        //  System.out.println( "ticket : " + ticket );
    }

    private void setSystemVariable() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            System.setProperty( "javax.net.ssl.trustStore",
                    classLoader.getResource( keyStore ).getFile() );
            System.setProperty( "javax.net.ssl.trustStorePassword",
                    keyStorePassword );
            System.setProperty( "javax.xml.bind.JAXBContext", "com.sun.xml.internal.bind.v2.ContextFactory" );
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
