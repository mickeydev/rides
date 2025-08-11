package com.communityrideshare.ride.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AcceptRideRequest {
    @NotNull
    private UUID driverId;
}
