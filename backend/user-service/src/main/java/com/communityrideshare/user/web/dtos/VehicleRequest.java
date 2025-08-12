package com.communityrideshare.user.web.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank
    private String make;
    @NotBlank
    private String model;
    @NotBlank
    private String licensePlate;
    @Min(1)
    private int seatsAvailable;
}
