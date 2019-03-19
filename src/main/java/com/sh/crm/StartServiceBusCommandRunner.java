package com.sh.crm;

import com.sh.crm.jpa.repos.notifications.EmailTemplatesRepo;
import com.sh.crm.jpa.repos.tickets.TicketsRepo;
import com.sh.crm.services.notification.Config;
import com.sh.crm.services.notification.consumer.NotificationConsumer;
import com.sh.crm.services.notification.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void run(String... args) throws Exception {
        eventBus.on( $( "NC" ), notificationConsumer );
        // notificationService.formatTemplate( 1, 19011500016l, "" );
        //  Ticket ticket = ticketsRepo.findById( Long.parseLong( "18121600001" ) ).orElse( null );

        //  System.out.println( "ticket : " + ticket );
    }
}
