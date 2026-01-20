package com.app.ticket;

public record TicketDto(
        Long id,

        int count,
        float price,

        float sum,

        Long raceId,
        String raceName,
        String raceDate,
        String raceImg,

        Long trackId,
        String trackName
) {
}
