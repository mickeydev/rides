package com.communityrideshare.ride.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RideRequest {
    @NotNull
    private UUID passengerId;

    @NotNull
    private PointDTO pickupLocation;

    @NotNull
    private PointDTO dropoffLocation;

    private LocalDateTime pickupTime; // For scheduled rides
}
