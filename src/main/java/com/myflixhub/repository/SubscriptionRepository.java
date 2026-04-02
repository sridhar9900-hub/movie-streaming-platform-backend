package com.myflixhub.repository;

import com.myflixhub.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
    Optional<Subscription> findByUserIdAndStatus(Long userId, Subscription.Status status);
    List<Subscription> findByEndDateBeforeAndStatus(LocalDate date, Subscription.Status status);
}
