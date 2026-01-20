package com.app.track;

import com.app.race.RaceDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TrackDto(
        Long id,

        @Size(min = 1, max = 255, message = "name is required length 1-255")
        @NotEmpty(message = "name is required")
        String name,
        @Min(value = 0, message = "length is required min 0.01")
        @Max(value = 1000000, message = "length is required min 1000000")
        float length,
        @Size(min = 1, max = 255, message = "address is required length 1-255")
        @NotEmpty(message = "address is required")
        String address,

        String img,

        List<RaceDto> races
) {
}
