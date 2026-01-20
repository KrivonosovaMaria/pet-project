package com.app.track.converter;

import com.app.race.converter.RaceToRaceDtoConverter;
import com.app.track.Track;
import com.app.track.TrackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrackToTrackDtoConverter implements Converter<Track, TrackDto> {

    private final RaceToRaceDtoConverter raceToRaceDtoConverter;

    @Override
    public TrackDto convert(Track source) {
        return new TrackDto(
                source.getId(),

                source.getName(),
                source.getLength(),
                source.getAddress(),

                source.getImg(),

                source.getRaces().stream().map(raceToRaceDtoConverter::convert).collect(Collectors.toList())
        );
    }
}
