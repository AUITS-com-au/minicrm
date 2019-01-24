package com.sh.crm.services.notification.consumer;

import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.services.notification.NotificationData;
import com.sh.crm.services.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class NotificationConsumer implements Consumer<Event<NotificationData>> {

    private static final Logger log = LoggerFactory.getLogger( NotificationConsumer.class );
    @Autowired
    private NotificationService notificationService;

    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {

        NotificationData notificationData = notificationDataEvent.getData();
        try {
            log.debug( "Initiate Notification >>> {} ", notificationData.toString() );
            notificationService.initiateNotification( notificationData );
        } catch (InterruptedException e) {
            LoggingUtils.logStackTrace( log, e, "error" );
        }

    }

}