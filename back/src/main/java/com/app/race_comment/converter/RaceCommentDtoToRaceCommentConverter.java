package com.app.race_comment.converter;

import com.app.race_comment.RaceComment;
import com.app.race_comment.RaceCommentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaceCommentDtoToRaceCommentConverter implements Converter<RaceCommentDto, RaceComment> {
    @Override
    public RaceComment convert(RaceCommentDto source) {
        return new RaceComment(
                source.text()
        );
    }
}
