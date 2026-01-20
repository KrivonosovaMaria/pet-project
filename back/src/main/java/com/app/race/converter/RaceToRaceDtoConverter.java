package com.app.race.converter;

import com.app.race.Race;
import com.app.race.RaceDto;
import com.app.race_comment.converter.RaceCommentToRaceCommentDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RaceToRaceDtoConverter implements Converter<Race, RaceDto> {

    private final RaceCommentToRaceCommentDtoConverter raceCommentToRaceCommentDtoConverter;

    @Override
    public RaceDto convert(Race source) {
        return new RaceDto(
                source.getId(),

                source.getName(),
                source.getCount(),
                source.getPrice(),
                source.getDate(),

                source.getDescription(),

                source.getImg(),

                source.getTrack().getId(),
                source.getTrack().getName(),
                source.getTrack().getImg(),

                source.getFree(),
                source.getFreePercent(),
                source.getBusy(),
                source.getBusyPercent(),
                source.getIncome(),
                source.getSalesRatio(),

                source.getComments().stream().map(raceCommentToRaceCommentDtoConverter::convert).collect(Collectors.toList())
        );
    }
}
