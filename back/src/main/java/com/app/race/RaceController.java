package com.app.race;

import com.app.race.converter.RaceDtoToRaceConverter;
import com.app.race.converter.RaceToRaceDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/races")
public class RaceController {

    private final RaceService service;
    private final RaceToRaceDtoConverter toDtoConverter;
    private final RaceDtoToRaceConverter toConverter;

    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.find(id))
        );
    }

    @Secured({ADMIN})
    @PostMapping
    public Result save(@Valid @RequestBody RaceDto saveDto, @RequestParam String trackId) {
        Race save = toConverter.convert(saveDto);
        Race saved = service.save(save, trackId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({ADMIN})
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody RaceDto updateDto, @RequestParam String trackId) {
        Race update = toConverter.convert(updateDto);
        Race updated = service.update(id, update, trackId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{id}/img")
    public Result updateImg(@PathVariable String id, @RequestParam MultipartFile file) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Img",
                toDtoConverter.convert(service.updateImg(id, file))
        );
    }

    @Secured({ADMIN})
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        service.delete(id);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Delete"
        );
    }

}
