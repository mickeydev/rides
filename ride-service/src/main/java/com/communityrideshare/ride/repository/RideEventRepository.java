package com.communityrideshare.ride.repository;

import com.communityrideshare.ride.domain.RideEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RideEventRepository extends JpaRepository<RideEvent, UUID> {
}
