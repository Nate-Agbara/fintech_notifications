package com.fintech.notification.repository;

import com.fintech.notification.model.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQueries;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author: Nathan
 */
@Repository
public interface NotificationRepository  extends CrudRepository<Notification, Long> {

    @Query(value = "select * from account where email_notification = 0 and id > ? order by id ",
    nativeQuery = true)
    @Transactional
    List<Notification> newAccounts(int id);

    @Query(value = "select id from last_treated where notification_type = 'email' ",
    nativeQuery = true)
    @Transactional
    int lastEmailTreated();

    @Modifying
    @Query(value = "update last_treated set id = ? where notification_type = 'email' "
            , nativeQuery = true)
    @Transactional
    void  setLastEmailTreated(int id);

    @Modifying
    @Query(value = "update account set email_notification = ? where account_no = ? and customerid = ? "
            , nativeQuery = true)
    @Transactional
    void  updateNotificationFlag(boolean email_notification, long account, long customerid);

    @Modifying
    @Query(value = "insert into notifications (account, recipient, message, type) values (?, ?, ?, ?) "
            , nativeQuery = true)
    @Transactional
    void  saveSentNotification(String account, String recipient, String message, String type);
}
