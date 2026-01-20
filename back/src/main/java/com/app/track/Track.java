package com.app.track;

import com.app.race.Race;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Track implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "track_g")
    @SequenceGenerator(name = "track_g", sequenceName = "track_seq", allocationSize = 1)
    private Long id;

    private String name;
    private float length;
    private String address;

    @Column(length = 1000)
    private String img = "";

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL)
    private List<Race> races = new ArrayList<>();

    public Track(Long id, String name, float length, String address) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.address = address;
    }

    public Track(String name, float length, String address) {
        this.name = name;
        this.length = length;
        this.address = address;
    }

    public void update(Track update) {
        this.name = update.getName();
        this.length = update.getLength();
        this.address = update.getAddress();
    }

}