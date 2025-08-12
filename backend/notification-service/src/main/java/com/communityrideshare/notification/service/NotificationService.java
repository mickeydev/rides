package com.communityrideshare.notification.service;

import com.communityrideshare.notification.domain.Notification;
import com.communityrideshare.notification.listener.dto.RideAcceptedEvent;
import com.communityrideshare.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void createRideAcceptedNotification(RideAcceptedEvent event) {
        // Create a notification for the passenger
        Notification passengerNotification = new Notification();
        passengerNotification.setUserId(event.getPassengerId());
        passengerNotification.setType(Notification.NotificationType.RIDE_UPDATE);
        passengerNotification.setTitle("Your ride has been accepted!");
        passengerNotification.setMessage("Your driver is on the way. Ride ID: " + event.getRideId());
        notificationRepository.save(passengerNotification);
    }
}
