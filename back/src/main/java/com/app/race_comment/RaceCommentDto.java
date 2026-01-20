package com.app.race_comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RaceCommentDto(
        Long id,

        String date,

        @Size(min = 1, max = 5000, message = "text is required length 1-5000")
        @NotEmpty(message = "text is required")
        String text
) {
}
