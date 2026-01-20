package com.app.notification;

public record NotificationDto(
        Long id,

        String date,

        String text,

        Long raceId,
        String raceName
) {
}
