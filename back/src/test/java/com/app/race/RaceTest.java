package com.app.race;

import com.app.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaceTest {

    @Mock
    RaceRepository repository;
    @InjectMocks
    RaceService service;

    List<Race> races = new ArrayList<>();

    @BeforeEach
    void setUp() {
        races.add(new Race(1L, "name1", 1, 1.99f, "date1", "description1"));
        races.add(new Race(2L, "name2", 2, 2.99f, "date2", "description2"));
        races.add(new Race(3L, "name3", 3, 3.99f, "date3", "description3"));
        races.add(new Race(4L, "name4", 4, 4.99f, "date4", "description4"));
    }

    @AfterEach
    void tearDown() {
        races.clear();
    }

    @Test
    void testFindAllSuccess() {
        given(service.findAll()).willReturn(races);

        List<Race> findAll = service.findAll();

        assertThat(findAll.size()).isEqualTo(races.size());

        verify(repository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Test
    void testFindSuccess() {
        Race race = races.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(race));

        Race find = service.find("1");

        assertThat(find.getId()).isEqualTo(race.getId());
        assertThat(find.getName()).isEqualTo(race.getName());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindNotFound() {
        given(repository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.find("1"));

        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testSaveSuccess() {
        Race save = races.get(0);

        given(repository.save(save)).willReturn(save);

        Race saved = service.saveForTest(save);

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo(save.getName());

        verify(repository, times(1)).save(save);
    }

    @Test
    void testUpdateSuccess() {
        Race old = races.get(0);

        Race update = new Race("name update", 10, 10.99f, "date update", "description update");

        given(repository.findById(1L)).willReturn(Optional.of(old));
        given(repository.save(old)).willReturn(old);

        Race updated = service.updateForTest(old.getId() + "", update);

        assertThat(updated.getId()).isEqualTo(1L);
        assertThat(updated.getName()).isEqualTo(update.getName());

        verify(repository, times(1)).findById(old.getId());
        verify(repository, times(1)).save(old);
    }

    @Test
    void testUpdateNotFound() {
        Race update = races.get(0);

        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.updateForTest("1", update));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testDeleteSuccess() {
        Race delete = races.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(delete));
        doNothing().when(repository).deleteById(1L);

        service.delete("1");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.delete("1"));

        verify(repository, times(1)).findById(1L);
    }

}
