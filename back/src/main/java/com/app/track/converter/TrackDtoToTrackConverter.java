package com.app.track.converter;

import com.app.track.Track;
import com.app.track.TrackDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrackDtoToTrackConverter implements Converter<TrackDto, Track> {
    @Override
    public Track convert(TrackDto source) {
        return new Track(
                source.name(),
                source.length(),
                source.address()
        );
    }
}
