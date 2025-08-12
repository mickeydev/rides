package com.communityrideshare.ride.web;

import com.communityrideshare.ride.domain.Ride;
import com.communityrideshare.ride.service.RideService;
import com.communityrideshare.ride.web.dtos.AcceptRideRequest;
import com.communityrideshare.ride.web.dtos.RideRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<Ride> createRide(@Valid @RequestBody RideRequest request) {
        Ride ride = rideService.createRide(request);
        return new ResponseEntity<>(ride, HttpStatus.CREATED);
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<Ride> getRide(@PathVariable UUID rideId) {
        Ride ride = rideService.getRide(rideId);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<Ride>> getAvailableRides() {
        List<Ride> rides = rideService.getAvailableRides();
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable UUID rideId, @Valid @RequestBody AcceptRideRequest request) {
        Ride ride = rideService.acceptRide(rideId, request.getDriverId());
        return ResponseEntity.ok(ride);
    }
}
