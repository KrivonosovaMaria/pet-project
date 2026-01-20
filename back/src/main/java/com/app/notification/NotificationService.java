package com.app.notification;

import com.app.appUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final UserService userService;

    public List<Notification> findAll() {
        return userService.getCurrentUser().getNotifications();
    }

}
