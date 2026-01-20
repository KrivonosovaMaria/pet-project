package com.app.notification;

import com.app.appUser.AppUser;
import com.app.race.Race;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static com.app.util.Global.getDateAndTimeNow;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Notification implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "notification_g")
    @SequenceGenerator(name = "notification_g", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    private String date = getDateAndTimeNow();

    private String text = "Данные обновлены!";

    @ManyToOne
    private Race race;
    @ManyToOne
    private AppUser owner;

    public Notification(Race race, AppUser owner) {
        this.race = race;
        this.owner = owner;
    }
}