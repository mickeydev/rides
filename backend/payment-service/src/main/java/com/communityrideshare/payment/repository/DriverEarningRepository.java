package com.communityrideshare.payment.repository;

import com.communityrideshare.payment.domain.DriverEarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverEarningRepository extends JpaRepository<DriverEarning, UUID> {
    Optional<DriverEarning> findByDriverId(UUID driverId);
}
