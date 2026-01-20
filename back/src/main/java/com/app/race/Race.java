package com.app.race;

import com.app.notification.Notification;
import com.app.race_comment.RaceComment;
import com.app.ticket.Ticket;
import com.app.track.Track;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.app.util.Global.round;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Race implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "race_g")
    @SequenceGenerator(name = "race_g", sequenceName = "race_seq", allocationSize = 1)
    private Long id;

    private String name;
    private int count;
    private float price;
    private String date;

    @Column(length = 5000)
    private String description;
    @Column(length = 1000)
    private String img = "";

    @ManyToOne
    private Track track;
    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    private List<RaceComment> comments = new ArrayList<>();

    public Race(Long id,String name, int count, float price, String date, String description) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.date = date;
        this.description = description;
    }

    public Race(String name, int count, float price, String date, String description) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.date = date;
        this.description = description;
    }

    public void update(Race update) {
        this.name = update.getName();
        this.count = update.getCount();
        this.price = update.getPrice();
        this.date = update.getDate();
        this.description = update.getDescription();
    }

    public int getFree() {
        return count - tickets.stream().reduce(0, (i, ticket) -> i + ticket.getCount(), Integer::sum);
    }

    public float getFreePercent() {
        return round((float) getFree() / count * 100);
    }

    public int getBusy() {
        return tickets.stream().reduce(0, (i, ticket) -> i + ticket.getCount(), Integer::sum);
    }

    public float getBusyPercent() {
        return round((float) getBusy() / count * 100);
    }

    public float getIncome() {
        return round(tickets.stream().reduce(0f, (i, ticket) -> i + ticket.getSum(), Float::sum));
    }

    public List<RaceComment> getComments() {
        comments.sort(Comparator.comparing(RaceComment::getId));
        Collections.reverse(comments);
        return comments;
    }

    public float getSalesRatio() {
        if (getBusy() == 0 || comments.isEmpty()) return 0;
        return round((float) getBusy() / comments.size());
    }

}