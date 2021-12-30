package com.fintech.notification.service;

import com.fintech.notification.model.Notification;
import com.fintech.notification.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: Nathan
 */
@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    public NotificationServiceImpl(){}

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> newAccounts(int id) {
        log.info("Inside service impl");
        return notificationRepository.newAccounts(id);
    }

    @Override
    public int getLastEmailTreated() {
        return notificationRepository.lastEmailTreated();
    }

    @Override
    public void setLastEmailTreated(int id) {
        notificationRepository.setLastEmailTreated(id);
    }

    @Override
    public void updateNotificationFlag(boolean email_notification, long account, long customerid) {
        notificationRepository.updateNotificationFlag(email_notification, account, customerid);
    }

    @Override
    public void saveSentNotification(String account, String recipient, String message, String type) {
        notificationRepository.saveSentNotification(account, recipient, message, type);
    }
}
