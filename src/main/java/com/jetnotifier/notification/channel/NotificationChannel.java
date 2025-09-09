package com.jetnotifier.notification.channel;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;

public interface NotificationChannel {
    boolean send(Notification notification, User user);

    String getChannelType();
}
