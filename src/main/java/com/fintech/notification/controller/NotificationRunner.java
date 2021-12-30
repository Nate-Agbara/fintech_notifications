package com.fintech.notification.controller;

import com.fintech.notification.model.Notification;
import com.fintech.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author: Nathan
 */
@Slf4j
@Component
@Scope("application")
public class NotificationRunner implements Runnable {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void run() {
        notification();
    }

    protected void notification() {
        while (true) {
            int lastEmailSent = notificationService.getLastEmailTreated();
            log.info("Listening for new account...");
            List<Notification> notifications = notificationService.newAccounts(lastEmailSent);
            if (!(notifications.isEmpty() || notifications == null)) {
                log.info("Found record");
                log.info("size of record {}", notifications.size());

                for (Notification notification : notifications) {
                    /**
                     * would be cleaner to define the message body in properties file I think.
                     */
                    String message = """
                            Hi {name}, 
                                                        
                            Welcome to Nathans!
                            Here's your account number {account}. Enjoy seamless transactions.
                                                        
                            Regards
                            """;
                    message = message.replace("{name}", notification.getFirstName())
                            .replace("{account}", String.valueOf(notification.getAccountNo()));
                    /**
                     * Logic to send mails goes in here
                     */
                    notificationService.updateNotificationFlag(true, notification.getAccountNo()
                            , notification.getCustomerid());

                    notificationService.saveSentNotification(String.valueOf(notification.getAccountNo()),
                            notification.getEmail(), message, "email");

                    notificationService.setLastEmailTreated(notification.getId());
                    //System.out.println(i + 1);
                    log.info("Notification sent for:- {} {}", notification.getEmail(), message);
                }

            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
