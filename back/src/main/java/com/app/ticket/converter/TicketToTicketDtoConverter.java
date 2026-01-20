package com.app.ticket.converter;

import com.app.ticket.Ticket;
import com.app.ticket.TicketDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TicketToTicketDtoConverter implements Converter<Ticket, TicketDto> {
    @Override
    public TicketDto convert(Ticket source) {
        return new TicketDto(
                source.getId(),

                source.getCount(),
                source.getPrice(),

                source.getSum(),

                source.getRace().getId(),
                source.getRace().getName(),
                source.getRace().getDate(),
                source.getRace().getImg(),

                source.getRace().getTrack().getId(),
                source.getRace().getTrack().getName()
        );
    }
}
