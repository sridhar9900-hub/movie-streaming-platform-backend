package com.myflixhub.dto;

import com.myflixhub.model.Subscription;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class SubscriptionRequest {

    @NotNull(message = "Plan is required")
    private Subscription.Plan plan;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    public SubscriptionRequest() {}

    public Subscription.Plan getPlan()  { return plan; }
    public LocalDate getStartDate()     { return startDate; }

    public void setPlan(Subscription.Plan plan) { this.plan = plan; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
}
