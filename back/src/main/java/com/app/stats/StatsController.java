package com.app.stats;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.ticket.Ticket;
import com.app.ticket.TicketService;
import com.app.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

import static com.app.util.Global.ADMIN;
import static com.app.util.Global.round;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Secured({ADMIN})
public class StatsController {

    private final TicketService ticketService;

    @GetMapping("/tickets/date")
    public Result ticketsDate() {
        LinkedHashMap<String, List<?>> res = new LinkedHashMap<>();

        List<String> names = new ArrayList<>();
        List<Float> values = new ArrayList<>();

        LocalDate date = LocalDate.now();

        date = date.minusMonths(2);
        String date1 = date.toString().substring(0, 7);
        date = date.plusMonths(1);
        String date2 = date.toString().substring(0, 7);
        date = date.plusMonths(1);
        String date3 = date.toString().substring(0, 7);

        date = date.plusMonths(1);
        String date4 = date.toString().substring(0, 7);


        float sum1 = round(ticketService.findAllByDateContaining(date1).stream().reduce(0f, (i, ticket) -> i + ticket.getSum(), Float::sum));
        float sum2 = round(ticketService.findAllByDateContaining(date2).stream().reduce(0f, (i, ticket) -> i + ticket.getSum(), Float::sum));
        float sum3 = round(ticketService.findAllByDateContaining(date3).stream().reduce(0f, (i, ticket) -> i + ticket.getSum(), Float::sum));

        float sum4 = round(sum3 + (((sum3 - sum2) + (sum2 - sum1)) / 2));

        names.add(date1);
        names.add(date2);
        names.add(date3);
        names.add(date4);

        values.add(sum1);
        values.add(sum2);
        values.add(sum3);
        values.add(sum4);

        System.out.println(sum1);
        System.out.println(sum2);
        System.out.println(sum3);
        System.out.println(sum4);

        res.put("names", names);
        res.put("values", values);

        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Tickets Date",
                Collections.unmodifiableMap(res)
        );
    }

}
