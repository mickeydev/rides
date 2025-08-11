package com.communityrideshare.payment.service;

import com.communityrideshare.payment.domain.DriverEarning;
import com.communityrideshare.payment.domain.Transaction;
import com.communityrideshare.payment.repository.DriverEarningRepository;
import com.communityrideshare.payment.repository.TransactionRepository;
import com.communityrideshare.payment.web.dtos.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final DriverEarningRepository driverEarningRepository;

    @Transactional
    public Transaction processTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setRideId(request.getRideId());
        transaction.setPayerId(request.getPayerId());
        transaction.setPayeeId(request.getPayeeId());
        transaction.setAmount(request.getAmount());
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED); // For MVP, we assume it's completed

        transactionRepository.save(transaction);

        // Update driver's earnings
        updateDriverEarnings(request.getPayeeId(), request.getAmount());

        return transaction;
    }

    private void updateDriverEarnings(UUID driverId, java.math.BigDecimal amount) {
        DriverEarning earning = driverEarningRepository.findByDriverId(driverId)
                .orElseGet(() -> {
                    DriverEarning newEarning = new DriverEarning();
                    newEarning.setDriverId(driverId);
                    return newEarning;
                });

        earning.setTotalEarned(earning.getTotalEarned().add(amount));
        driverEarningRepository.save(earning);
    }
}
