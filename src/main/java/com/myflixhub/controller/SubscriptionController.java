package com.myflixhub.controller;

import com.myflixhub.dto.SubscriptionRequest;
import com.myflixhub.model.Subscription;
import com.myflixhub.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // ✅ Constructor Injection (Correct Way)
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<Subscription> subscribe(@Valid @RequestBody SubscriptionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subscriptionService.subscribe(request));
    }

    @GetMapping("/active")
    public ResponseEntity<Subscription> getActiveSubscription() {
        return ResponseEntity.ok(subscriptionService.getActiveSubscription());
    }

    @GetMapping("/my")
    public ResponseEntity<List<Subscription>> getMySubscriptions() {
        return ResponseEntity.ok(subscriptionService.getMySubscriptions());
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Subscription> cancelSubscription(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.cancelSubscription(id));
    }
}