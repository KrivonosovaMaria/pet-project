package com.app.race_comment;

import com.app.race_comment.converter.RaceCommentDtoToRaceCommentConverter;
import com.app.race_comment.converter.RaceCommentToRaceCommentDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.app.util.Global.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/races/comments")
public class RaceCommentController {

    private final RaceCommentService service;
    private final RaceCommentToRaceCommentDtoConverter toDtoConverter;
    private final RaceCommentDtoToRaceCommentConverter toConverter;

    @Secured({USER})
    @PostMapping
    public Result save(@Valid @RequestBody RaceCommentDto saveDto, @RequestParam String raceId) {
        RaceComment save = toConverter.convert(saveDto);
        RaceComment saved = service.save(save, raceId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

}
