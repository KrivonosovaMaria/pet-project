package com.app.race_comment;

import com.app.appUser.AppUser;
import com.app.race.Race;
import com.app.util.Global;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RaceComment implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "race_comment_g")
    @SequenceGenerator(name = "race_comment_g", sequenceName = "race_comment_seq", allocationSize = 1)
    private Long id;

    private String date = Global.getDateAndTimeNow();

    @Column(length = 5000)
    private String text;

    @ManyToOne
    private Race race;
    @ManyToOne
    private AppUser owner;

    public RaceComment(String text) {
        this.text = text;
    }
}