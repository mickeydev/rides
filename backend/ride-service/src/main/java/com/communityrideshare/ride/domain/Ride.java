package com.communityrideshare.ride.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID driverId;

    private UUID passengerId;

    @Column(nullable = false, columnDefinition = "geography(Point,4326)")
    private Point pickupLocation;

    @Column(nullable = false, columnDefinition = "geography(Point,4326)")
    private Point dropoffLocation;

    private LocalDateTime pickupTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RideStatus status;

    private BigDecimal fareEstimate;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = RideStatus.REQUESTED;
        }
    }

    public enum RideStatus {
        REQUESTED, ACCEPTED, IN_PROGRESS, COMPLETED, CANCELLED
    }
}
