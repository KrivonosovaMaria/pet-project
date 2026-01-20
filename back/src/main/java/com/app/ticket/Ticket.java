package com.app.ticket;

import com.app.appUser.AppUser;
import com.app.race.Race;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static com.app.util.Global.getDateNow;
import static com.app.util.Global.round;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ticket implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ticket_g")
    @SequenceGenerator(name = "ticket_g", sequenceName = "ticket_seq", allocationSize = 1)
    private Long id;

    private String date = getDateNow();

    private int count;
    private float price;

    @ManyToOne
    private Race race;
    @ManyToOne
    private AppUser owner;

    public Ticket(int count, Race race, AppUser owner) {
        this.count = count;
        this.price = race.getPrice();
        this.race = race;
        this.owner = owner;
    }

    public float getSum() {
        return round(count * price);
    }

}