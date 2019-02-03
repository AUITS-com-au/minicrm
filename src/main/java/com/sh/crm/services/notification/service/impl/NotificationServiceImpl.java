package com.sh.crm.services.notification.service.impl;

import com.sh.crm.services.notification.NotificationData;
import com.sh.crm.services.notification.service.NotificationHandler;
import com.sh.crm.services.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    protected static final Logger logger = LoggerFactory.getLogger( NotificationServiceImpl.class );

    @Autowired
    private NotificationHandler notificationHandler;

    @Override
    public void initiateNotification(NotificationData notificationData)
            throws InterruptedException {
        logger.debug( "Logger Service Received Notification Data >> {}", notificationData );
        long currentMills = System.currentTimeMillis();

        switch (notificationData.getNotificationType()) {
            case NotificationType.ACTION:
                //handle action
                notificationHandler.handleAction( notificationData.getActionId() );
                break;
            default:
                logger.error( "Notification type {} is not mapped please fix it", notificationData.getNotificationType() );
                break;
        }
        currentMills = System.currentTimeMillis() - currentMills;
        logger.debug( "Logger Service Completed Operation In {} Mills", currentMills );
    }
}