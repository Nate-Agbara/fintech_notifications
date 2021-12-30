package com.fintech.notification.service;

import com.fintech.notification.model.Notification;

import java.util.List;
import java.util.Optional;

/**
 * @author: Nathan
 */
public interface NotificationService {

    List<Notification> newAccounts(int id);

    int getLastEmailTreated();

    void  setLastEmailTreated(int id);

    void  updateNotificationFlag(boolean email_notification, long account, long customerid);

    void  saveSentNotification(String account, String recipient, String message, String type);
}
