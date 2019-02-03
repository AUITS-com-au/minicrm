package com.sh.crm.services.notification;

import com.sh.crm.services.notification.service.impl.NotificationType;

public class NotificationData {
    private Long actionId;
    private Integer slaID;
    private Long ticketID;
    private int notificationType;

    public NotificationData() {
        this.notificationType = NotificationType.ACTION;
    }

    public NotificationData(Long actionId, int notificationType) {
        this.actionId = actionId;
        this.notificationType = notificationType;
    }

    public NotificationData(Long actionId) {
        this.actionId = actionId;
        this.notificationType = NotificationType.ACTION;
    }

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Integer getSlaID() {
        return slaID;
    }

    public void setSlaID(Integer slaID) {
        this.slaID = slaID;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public String toString() {
        return "NotificationData{" +
                "actionId=" + actionId +
                ", esclationID=" + slaID +
                ", notificationType=" + notificationType +
                '}';
    }
}
