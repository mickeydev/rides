package com.communityrideshare.ride.service;

import com.communityrideshare.ride.domain.Ride;
import com.communityrideshare.ride.repository.RideRepository;
import com.communityrideshare.ride.web.dtos.PointDTO;
import com.communityrideshare.ride.web.dtos.RideRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional
    public Ride createRide(RideRequest request) {
        Ride ride = new Ride();
        ride.setPassengerId(request.getPassengerId());
        ride.setPickupLocation(createPoint(request.getPickupLocation()));
        ride.setDropoffLocation(createPoint(request.getDropoffLocation()));
        ride.setPickupTime(request.getPickupTime());
        // For now, we'll assign a dummy driver ID. In a real scenario, this would be null
        // until a driver accepts the ride.
        ride.setDriverId(UUID.randomUUID());
        return rideRepository.save(ride);
    }

    @Transactional(readOnly = true)
    public Ride getRide(UUID rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found")); // Replace with a proper exception
    }

    private Point createPoint(PointDTO dto) {
        return geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
    }
}
