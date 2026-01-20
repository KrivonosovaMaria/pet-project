package com.app.notification.converter;

import com.app.notification.Notification;
import com.app.notification.NotificationDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationDtoConverter implements Converter<Notification, NotificationDto> {
    @Override
    public NotificationDto convert(Notification source) {
        return new NotificationDto(
                source.getId(),

                source.getDate(),

                source.getText(),

                source.getRace().getId(),
                source.getRace().getName()
        );
    }
}
