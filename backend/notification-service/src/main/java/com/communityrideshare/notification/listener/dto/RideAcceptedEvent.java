package com.communityrideshare.notification.listener.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class RideAcceptedEvent implements Serializable {
    private UUID rideId;
    private UUID driverId;
    private UUID passengerId;
}
