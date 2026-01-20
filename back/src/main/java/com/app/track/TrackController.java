package com.app.track;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.track.converter.TrackDtoToTrackConverter;
import com.app.track.converter.TrackToTrackDtoConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService service;
    private final TrackToTrackDtoConverter toDtoConverter;
    private final TrackDtoToTrackConverter toConverter;

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
    public Result save(@Valid @RequestBody TrackDto saveDto) {
        Track save = toConverter.convert(saveDto);
        Track saved = service.save(save);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({ADMIN})
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody TrackDto updateDto) {
        Track update = toConverter.convert(updateDto);
        Track updated = service.update(id, update);
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
