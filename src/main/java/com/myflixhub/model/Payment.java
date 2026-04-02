package com.myflixhub.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(unique = true)
    private String transactionId;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Payment() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ─── Getters ────────────────────────────────────────────────────
    public Long getId()                   { return id; }
    public User getUser()                 { return user; }
    public Subscription getSubscription() { return subscription; }
    public BigDecimal getAmount()         { return amount; }
    public String getCurrency()           { return currency; }
    public PaymentStatus getStatus()      { return status; }
    public String getTransactionId()      { return transactionId; }
    public String getPaymentMethod()      { return paymentMethod; }
    public LocalDateTime getCreatedAt()   { return createdAt; }

    // ─── Setters ────────────────────────────────────────────────────
    public void setId(Long id)                        { this.id = id; }
    public void setUser(User user)                    { this.user = user; }
    public void setSubscription(Subscription s)       { this.subscription = s; }
    public void setAmount(BigDecimal amount)          { this.amount = amount; }
    public void setCurrency(String currency)          { this.currency = currency; }
    public void setStatus(PaymentStatus status)       { this.status = status; }
    public void setTransactionId(String transactionId){ this.transactionId = transactionId; }
    public void setPaymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ─── Builder ────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private User user;
        private Subscription subscription;
        private BigDecimal amount;
        private String currency;
        private PaymentStatus status;
        private String transactionId;
        private String paymentMethod;

        public Builder id(Long id)                        { this.id = id; return this; }
        public Builder user(User user)                    { this.user = user; return this; }
        public Builder subscription(Subscription s)       { this.subscription = s; return this; }
        public Builder amount(BigDecimal amount)          { this.amount = amount; return this; }
        public Builder currency(String currency)          { this.currency = currency; return this; }
        public Builder status(PaymentStatus status)       { this.status = status; return this; }
        public Builder transactionId(String transactionId){ this.transactionId = transactionId; return this; }
        public Builder paymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod; return this; }

        public Payment build() {
            Payment p = new Payment();
            p.id = this.id;
            p.user = this.user;
            p.subscription = this.subscription;
            p.amount = this.amount;
            p.currency = this.currency;
            p.status = this.status;
            p.transactionId = this.transactionId;
            p.paymentMethod = this.paymentMethod;
            return p;
        }
    }

    // ─── Enum ───────────────────────────────────────────────────────
    public enum PaymentStatus {
        PENDING, SUCCESS, FAILED, REFUNDED
    }
}
