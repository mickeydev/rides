package com.communityrideshare.payment.web.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequest {
    @NotNull
    private UUID rideId;
    @NotNull
    private UUID payerId;
    @NotNull
    private UUID payeeId;
    @NotNull
    private BigDecimal amount;
}
