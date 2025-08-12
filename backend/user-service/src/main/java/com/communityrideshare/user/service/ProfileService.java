package com.communityrideshare.user.service;

import com.communityrideshare.user.domain.Profile;
import com.communityrideshare.user.domain.Vehicle;
import com.communityrideshare.user.repository.ProfileRepository;
import com.communityrideshare.user.repository.VehicleRepository;
import com.communityrideshare.user.web.dtos.ProfileRequest;
import com.communityrideshare.user.web.dtos.VehicleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public Profile createProfile(UUID userId, ProfileRequest request) {
        if (profileRepository.existsById(userId)) {
            throw new IllegalStateException("Profile for this user already exists.");
        }
        Profile profile = new Profile();
        profile.setId(userId);
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setProfilePhotoUrl(request.getProfilePhotoUrl());
        if (request.getRole() != null) {
            profile.setRole(request.getRole());
        }
        return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Profile getProfile(UUID userId) {
        return profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found")); // Replace with a proper exception
    }

    @Transactional
    public Profile updateProfile(UUID userId, ProfileRequest request) {
        Profile profile = getProfile(userId);
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setProfilePhotoUrl(request.getProfilePhotoUrl());
        if (request.getRole() != null) {
            profile.setRole(request.getRole());
        }
        return profileRepository.save(profile);
    }

    @Transactional
    public Vehicle addVehicle(UUID userId, VehicleRequest request) {
        Profile profile = getProfile(userId);
        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(profile);
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setSeatsAvailable(request.getSeatsAvailable());
        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehicles(UUID userId) {
        return vehicleRepository.findByOwnerId(userId);
    }
}
