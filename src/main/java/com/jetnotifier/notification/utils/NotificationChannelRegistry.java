package com.jetnotifier.notification.utils;

import com.jetnotifier.notification.channel.NotificationChannel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class NotificationChannelRegistry {
    private final Map<String, NotificationChannel> channelMap;

    @Autowired
    public NotificationChannelRegistry(List<NotificationChannel> channelMap) {
        this.channelMap = channelMap.stream().collect(Collectors.toMap(NotificationChannel::getChannelType, c->c));
    }

    public NotificationChannel getChannel(String type) {
        return channelMap.get(type);
    }
}
