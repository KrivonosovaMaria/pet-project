package com.app.race_comment.converter;

import com.app.race_comment.RaceComment;
import com.app.race_comment.RaceCommentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaceCommentToRaceCommentDtoConverter implements Converter<RaceComment, RaceCommentDto> {
    @Override
    public RaceCommentDto convert(RaceComment source) {
        return new RaceCommentDto(
                source.getId(),

                source.getDate(),

                source.getText()
        );
    }
}
