package com.app.race;

import com.app.race_comment.RaceCommentDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RaceDto(
        Long id,

        @Size(min = 1, max = 255, message = "name is required length 1-255")
        @NotEmpty(message = "name is required")
        String name,
        @Min(value = 1, message = "count is required min 1")
        @Max(value = 1000000, message = "count is required max 1000000")
        int count,
        @Min(value = 0, message = "price is required min 0.01")
        @Max(value = 1000000, message = "price is required max 1000000")
        float price,
        @Size(min = 1, max = 255, message = "date is required length 1-255")
        @NotEmpty(message = "date is required")
        String date,

        @Size(min = 1, max = 5000, message = "description is required length 1-5000")
        @NotEmpty(message = "description is required")
        String description,

        String img,

        Long trackId,
        String trackName,
        String trackImg,

        int free,
        float freePercent,
        int busy,
        float busyPercent,
        float income,
        float salesRatio,

        List<RaceCommentDto> comments
) {
}
