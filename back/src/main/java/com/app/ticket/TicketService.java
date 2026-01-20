package com.app.ticket;

import com.app.appUser.AppUser;
import com.app.appUser.UserService;
import com.app.race.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final RaceService raceService;
    private final UserService userService;

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        AppUser user = userService.getCurrentUser();

        switch (user.getRole()) {
            case ADMIN -> tickets = repository.findAll();
            case USER -> tickets = user.getTickets();
        }

        tickets.sort(Comparator.comparing(Ticket::getId));
        Collections.reverse(tickets);

        return tickets;
    }

    public List<Ticket> findAllByDateContaining(String date) {
        return repository.findAllByDateContaining(date);
    }

    public Ticket save(int count, String raceId) {
        return repository.save(new Ticket(count, raceService.find(raceId), userService.getCurrentUser()));
    }

}
