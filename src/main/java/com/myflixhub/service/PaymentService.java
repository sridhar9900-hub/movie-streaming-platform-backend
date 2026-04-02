package com.myflixhub.service;

import com.myflixhub.dto.PaymentRequest;
import com.myflixhub.exception.CustomException;
import com.myflixhub.model.Payment;
import com.myflixhub.model.Subscription;
import com.myflixhub.model.User;
import com.myflixhub.repository.PaymentRepository;
import com.myflixhub.repository.SubscriptionRepository;
import com.myflixhub.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          SubscriptionRepository subscriptionRepository,
                          UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Payment processPayment(PaymentRequest request) {
        User user = getCurrentUser();

        Subscription subscription = subscriptionRepository.findById(request.getSubscriptionId())
                .orElseThrow(() -> new CustomException("Subscription not found", HttpStatus.NOT_FOUND));

        if (!subscription.getUser().getId().equals(user.getId())) {
            throw new CustomException("Access denied to this subscription", HttpStatus.FORBIDDEN);
        }

        boolean paymentSuccess = simulatePaymentGateway();

        Payment payment = Payment.builder()
                .user(user)
                .subscription(subscription)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .transactionId(UUID.randomUUID().toString())
                .status(paymentSuccess ? Payment.PaymentStatus.SUCCESS : Payment.PaymentStatus.FAILED)
                .build();

        return paymentRepository.save(payment);
    }

    public List<Payment> getMyPayments() {
        User user = getCurrentUser();
        return paymentRepository.findByUserId(user.getId());
    }

    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new CustomException("Payment not found for transaction: " + transactionId, HttpStatus.NOT_FOUND));
    }

    public Payment refundPayment(Long paymentId) {
        User user = getCurrentUser();

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new CustomException("Payment not found", HttpStatus.NOT_FOUND));

        if (!payment.getUser().getId().equals(user.getId())) {
            throw new CustomException("Access denied to this payment", HttpStatus.FORBIDDEN);
        }
        if (payment.getStatus() != Payment.PaymentStatus.SUCCESS) {
            throw new CustomException("Only successful payments can be refunded", HttpStatus.BAD_REQUEST);
        }

        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    private boolean simulatePaymentGateway() {
        return true;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", HttpStatus.NOT_FOUND));
    }
}
