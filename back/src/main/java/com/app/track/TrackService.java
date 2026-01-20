package com.app.track;

import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository repository;

    public List<Track> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Track find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найдена трасса по ИД: " + id));
    }

    public Track save(Track save) {
        return repository.save(save);
    }

    public Track update(String id, Track update) {
        Track old = find(id);
        old.update(update);
        return repository.save(old);
    }

    public Track updateImg(String id, MultipartFile img) {
        Track track = find(id);
        try {
            track.setImg(saveFile(img, "track"));
        } catch (IOException e) {
            if (track.getImg().isEmpty()) repository.deleteById(track.getId());
            throw new BadRequestException("Некорректное изображение!");
        }
        return repository.save(track);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }

}
