package com.communityrideshare.ride.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideAcceptedEvent implements Serializable {
    private UUID rideId;
    private UUID driverId;
    private UUID passengerId;
}
