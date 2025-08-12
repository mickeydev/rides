package com.communityrideshare.ride.service;

import com.communityrideshare.ride.config.RabbitMQConfig;
import com.communityrideshare.ride.domain.Ride;
import com.communityrideshare.ride.repository.RideRepository;
import com.communityrideshare.ride.service.dto.RideAcceptedEvent;
import com.communityrideshare.ride.web.dtos.PointDTO;
import com.communityrideshare.ride.web.dtos.RideRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RabbitTemplate rabbitTemplate;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional
    public Ride createRide(RideRequest request) {
        Ride ride = new Ride();
        ride.setPassengerId(request.getPassengerId());
        ride.setPickupLocation(createPoint(request.getPickupLocation()));
        ride.setDropoffLocation(createPoint(request.getDropoffLocation()));
        ride.setPickupTime(request.getPickupTime());
        ride.setStatus(Ride.RideStatus.REQUESTED);
        return rideRepository.save(ride);
    }

    @Transactional(readOnly = true)
    public Ride getRide(UUID rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found")); // Replace with a proper exception
    }

    @Transactional(readOnly = true)
    public List<Ride> getAvailableRides() {
        return rideRepository.findByStatus(Ride.RideStatus.REQUESTED);
    }

    @Transactional
    public Ride acceptRide(UUID rideId, UUID driverId) {
        Ride ride = getRide(rideId);
        if (ride.getStatus() != Ride.RideStatus.REQUESTED) {
            throw new IllegalStateException("Ride is not available to be accepted.");
        }
        ride.setDriverId(driverId);
        ride.setStatus(Ride.RideStatus.ACCEPTED);
        Ride savedRide = rideRepository.save(ride);

        // Publish event to RabbitMQ
        RideAcceptedEvent event = new RideAcceptedEvent(savedRide.getId(), savedRide.getDriverId(), savedRide.getPassengerId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "ride.accepted", event);

        return savedRide;
    }

    private Point createPoint(PointDTO dto) {
        return geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
    }
}
