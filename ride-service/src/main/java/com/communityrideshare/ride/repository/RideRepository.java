package com.communityrideshare.ride.repository;

import com.communityrideshare.ride.domain.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {
    List<Ride> findByDriverId(UUID driverId);
    List<Ride> findByPassengerId(UUID passengerId);
    List<Ride> findByStatus(Ride.RideStatus status);
}
