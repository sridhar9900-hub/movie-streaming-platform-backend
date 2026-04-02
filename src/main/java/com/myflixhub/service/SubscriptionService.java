package com.myflixhub.service;

import com.myflixhub.dto.SubscriptionRequest;
import com.myflixhub.exception.CustomException;
import com.myflixhub.model.Subscription;
import com.myflixhub.model.User;
import com.myflixhub.repository.SubscriptionRepository;
import com.myflixhub.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    // Constructor
    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Subscription subscribe(SubscriptionRequest request) {
        User user = getCurrentUser();

        // Cancel existing active subscription
        subscriptionRepository.findByUserIdAndStatus(user.getId(), Subscription.Status.ACTIVE)
                .ifPresent(existing -> {
                    existing.setStatus(Subscription.Status.CANCELLED);
                    subscriptionRepository.save(existing);
                });

        LocalDate endDate = calculateEndDate(request.getPlan(), request.getStartDate());

        // ❌ Removed builder → using setters
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(request.getPlan());
        subscription.setStatus(Subscription.Status.ACTIVE);
        subscription.setStartDate(request.getStartDate());
        subscription.setEndDate(endDate);

        return subscriptionRepository.save(subscription);
    }

    public Subscription getActiveSubscription() {
        User user = getCurrentUser();

        return subscriptionRepository
                .findByUserIdAndStatus(user.getId(), Subscription.Status.ACTIVE)
                .orElseThrow(() ->
                        new CustomException("No active subscription found", HttpStatus.NOT_FOUND));
    }

    public List<Subscription> getMySubscriptions() {
        User user = getCurrentUser();
        return subscriptionRepository.findByUserId(user.getId());
    }

    public Subscription cancelSubscription(Long subscriptionId) {
        User user = getCurrentUser();

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new CustomException("Subscription not found", HttpStatus.NOT_FOUND));

        if (!subscription.getUser().getId().equals(user.getId())) {
            throw new CustomException("Access denied to this subscription", HttpStatus.FORBIDDEN);
        }

        if (subscription.getStatus() != Subscription.Status.ACTIVE) {
            throw new CustomException("Subscription is not active", HttpStatus.BAD_REQUEST);
        }

        subscription.setStatus(Subscription.Status.CANCELLED);
        return subscriptionRepository.save(subscription);
    }

    private LocalDate calculateEndDate(Subscription.Plan plan, LocalDate startDate) {
        switch (plan) {
            case BASIC:
                return startDate.plusMonths(1);
            case STANDARD:
                return startDate.plusMonths(3);
            case PREMIUM:
                return startDate.plusMonths(12);
            default:
                throw new IllegalArgumentException("Invalid plan");
        }
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomException("Authenticated user not found", HttpStatus.NOT_FOUND));
    }
}