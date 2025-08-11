package com.communityrideshare.rating.web.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RatingRequest {
    @NotNull
    private UUID rideId;
    @NotNull
    private UUID ratedBy;
    @NotNull
    private UUID ratedUser;
    @Min(1)
    @Max(5)
    private int rating;
    private String comment;
}
