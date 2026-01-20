package com.app.race;

import com.app.appUser.AppUser;
import com.app.notification.Notification;
import com.app.notification.NotificationRepository;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import com.app.ticket.Ticket;
import com.app.track.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class RaceService {

    private final RaceRepository repository;
    private final TrackService trackService;
    private final NotificationRepository notificationRepository;

    public List<Race> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Race find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найдена трасса по ИД: " + id));
    }

    public Race save(Race save, String trackId) {
        save.setTrack(trackService.find(trackId));
        return repository.save(save);
    }

    public Race saveForTest(Race save) {
        return repository.save(save);
    }

    public Race update(String id, Race update, String trackId) {
        Race old = find(id);
        old.setTrack(trackService.find(trackId));
        old.update(update);

        Map<Long, AppUser> users = new HashMap<>();
        for (Ticket i : old.getTickets()) users.put(i.getOwner().getId(), i.getOwner());
        for (AppUser i : users.values()) notificationRepository.save(new Notification(old, i));

        return repository.save(old);
    }

    public Race updateForTest(String id, Race update) {
        Race old = find(id);
        old.update(update);
        return repository.save(old);
    }

    public Race updateImg(String id, MultipartFile img) {
        Race race = find(id);
        try {
            race.setImg(saveFile(img, "race"));
        } catch (IOException e) {
            if (race.getImg().isEmpty()) repository.deleteById(race.getId());
            throw new BadRequestException("Некорректное изображение!");
        }
        return repository.save(race);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }
}
