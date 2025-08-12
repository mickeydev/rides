package com.communityrideshare.user.web;

import com.communityrideshare.user.domain.Profile;
import com.communityrideshare.user.domain.Vehicle;
import com.communityrideshare.user.service.ProfileService;
import com.communityrideshare.user.web.dtos.ProfileRequest;
import com.communityrideshare.user.web.dtos.VehicleRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // Note: In a real setup, the userId would be extracted from the JWT token
    // instead of being passed in the path for profile creation.
    @PostMapping("/{userId}")
    public ResponseEntity<Profile> createProfile(@PathVariable UUID userId, @Valid @RequestBody ProfileRequest request) {
        Profile profile = profileService.createProfile(userId, request);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfile(@PathVariable UUID userId) {
        Profile profile = profileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable UUID userId, @Valid @RequestBody ProfileRequest request) {
        Profile profile = profileService.updateProfile(userId, request);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/{userId}/vehicles")
    public ResponseEntity<Vehicle> addVehicle(@PathVariable UUID userId, @Valid @RequestBody VehicleRequest request) {
        Vehicle vehicle = profileService.addVehicle(userId, request);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/vehicles")
    public ResponseEntity<List<Vehicle>> getVehicles(@PathVariable UUID userId) {
        List<Vehicle> vehicles = profileService.getVehicles(userId);
        return ResponseEntity.ok(vehicles);
    }
}
