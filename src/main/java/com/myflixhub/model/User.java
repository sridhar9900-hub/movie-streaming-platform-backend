package com.myflixhub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    // ─── Constructors ───────────────────────────────────────────────
    public User() {}

    public User(Long id, String email, String password, String fullName,
                Role role, LocalDateTime createdAt,
                List<Subscription> subscriptions, List<Payment> payments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
        this.subscriptions = subscriptions;
        this.payments = payments;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ─── Getters ────────────────────────────────────────────────────
    public Long getId()                        { return id; }
    public String getEmail()                   { return email; }
    public String getPassword()                { return password; }
    public String getFullName()                { return fullName; }
    public Role getRole()                      { return role; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public List<Subscription> getSubscriptions() { return subscriptions; }
    public List<Payment> getPayments()         { return payments; }

    // ─── Setters ────────────────────────────────────────────────────
    public void setId(Long id)                         { this.id = id; }
    public void setEmail(String email)                 { this.email = email; }
    public void setPassword(String password)           { this.password = password; }
    public void setFullName(String fullName)           { this.fullName = fullName; }
    public void setRole(Role role)                     { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt)  { this.createdAt = createdAt; }
    public void setSubscriptions(List<Subscription> s) { this.subscriptions = s; }
    public void setPayments(List<Payment> p)           { this.payments = p; }

    // ─── Builder ────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String email;
        private String password;
        private String fullName;
        private Role role;
        private LocalDateTime createdAt;
        private List<Subscription> subscriptions;
        private List<Payment> payments;

        public Builder id(Long id)                            { this.id = id; return this; }
        public Builder email(String email)                    { this.email = email; return this; }
        public Builder password(String password)              { this.password = password; return this; }
        public Builder fullName(String fullName)              { this.fullName = fullName; return this; }
        public Builder role(Role role)                        { this.role = role; return this; }
        public Builder createdAt(LocalDateTime createdAt)     { this.createdAt = createdAt; return this; }
        public Builder subscriptions(List<Subscription> s)   { this.subscriptions = s; return this; }
        public Builder payments(List<Payment> p)              { this.payments = p; return this; }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.email = this.email;
            user.password = this.password;
            user.fullName = this.fullName;
            user.role = this.role;
            user.createdAt = this.createdAt;
            user.subscriptions = this.subscriptions;
            user.payments = this.payments;
            return user;
        }
    }

    // ─── Role Enum ──────────────────────────────────────────────────
    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
}
