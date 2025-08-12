package com.communityrideshare.notification.listener;

import com.communityrideshare.notification.config.RabbitMQConfig;
import com.communityrideshare.notification.listener.dto.RideAcceptedEvent;
import com.communityrideshare.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RideEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleRideAcceptedEvent(RideAcceptedEvent event) {
        log.info("Received ride accepted event: {}", event);
        try {
            notificationService.createRideAcceptedNotification(event);
            log.info("Notification created for ride: {}", event.getRideId());
        } catch (Exception e) {
            log.error("Error processing ride accepted event: {}", event, e);
            // In a real application, you might want to send the message to a dead-letter queue.
        }
    }
}
