package com.fintech.notification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: Nathan
 */
@Slf4j
@Component
public class NotificationController {

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ApplicationContext applicationContext;

    private Boolean debug = true;

    @PostConstruct
    public void atStartup() {
        NotificationRunner notificationRunner = applicationContext.getBean(NotificationRunner.class);
        taskExecutor.execute(notificationRunner );
        if (debug) {
            log.warn("###### Startup ok");
        }
    }
}
