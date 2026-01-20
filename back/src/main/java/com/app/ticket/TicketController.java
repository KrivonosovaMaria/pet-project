package com.app.ticket;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.ticket.converter.TicketToTicketDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.ADMIN;
import static com.app.util.Global.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;
    private final TicketToTicketDtoConverter toDtoConverter;

    @Secured({ADMIN, USER})
    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({USER})
    @PostMapping
    public Result save(@RequestParam int count, @RequestParam String raceId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(service.save(count, raceId))
        );
    }

}
