package com.app.appUser;

import com.app.enums.Role;
import com.app.notification.Notification;
import com.app.race_comment.RaceComment;
import com.app.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "app_user_g")
    @SequenceGenerator(name = "app_user_g", sequenceName = "app_user_seq", allocationSize = 1)
    private Long id;

    @Size(min = 1, max = 255, message = "username is required length 1-255")
    @NotEmpty(message = "username is required")
    private String username;
    @Size(min = 1, max = 255, message = "password is required length 1-255")
    @NotEmpty(message = "password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<RaceComment> raceComments = new ArrayList<>();

    public AppUser(String username) {
        this.username = username;
    }

    public void set(AppUser user) {

    }

    public List<Notification> getNotifications() {
        notifications.sort(Comparator.comparing(Notification::getId));
        Collections.reverse(notifications);
        return notifications;
    }
}