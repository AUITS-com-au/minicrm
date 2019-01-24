package com.sh.crm.services.notification.service;

import com.sh.crm.services.notification.NotificationData;

public interface NotificationService {

    void initiateNotification(NotificationData notificationData)
            throws InterruptedException;

}