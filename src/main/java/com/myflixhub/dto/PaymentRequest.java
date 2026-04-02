package com.myflixhub.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull(message = "Subscription ID is required")
    private Long subscriptionId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    public PaymentRequest() {}

    public Long getSubscriptionId()     { return subscriptionId; }
    public BigDecimal getAmount()       { return amount; }
    public String getCurrency()         { return currency; }
    public String getPaymentMethod()    { return paymentMethod; }

    public void setSubscriptionId(Long subscriptionId)   { this.subscriptionId = subscriptionId; }
    public void setAmount(BigDecimal amount)             { this.amount = amount; }
    public void setCurrency(String currency)             { this.currency = currency; }
    public void setPaymentMethod(String paymentMethod)   { this.paymentMethod = paymentMethod; }
}
