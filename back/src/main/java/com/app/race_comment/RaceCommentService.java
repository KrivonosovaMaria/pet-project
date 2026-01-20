package com.app.race_comment;

import com.app.appUser.UserService;
import com.app.race.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaceCommentService {

    private final RaceCommentRepository repository;
    private final RaceService raceService;
    private final UserService userService;

    public RaceComment save(RaceComment save, String raceId) {
        save.setRace(raceService.find(raceId));
        save.setOwner(userService.getCurrentUser());
        return repository.save(save);
    }

}
