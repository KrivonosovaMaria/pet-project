package com.app.race.converter;

import com.app.race.Race;
import com.app.race.RaceDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaceDtoToRaceConverter implements Converter<RaceDto, Race> {
    @Override
    public Race convert(RaceDto source) {
        return new Race(
                source.name(),
                source.count(),
                source.price(),
                source.date(),
                source.description()
        );
    }
}
