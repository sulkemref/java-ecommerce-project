package com.mycompany.discount;

import com.mycompany.*;

import java.util.UUID;


public abstract class Discount {

    private UUID id;
    private String name;
    private Double thresholdAmount;

    public Discount(UUID id, String name, Double thresholdAmount) {
        this.id = id;
        this.name = name;
        this.thresholdAmount = thresholdAmount;
    }

    public boolean decideDiscountIsApplicableToCart(Cart cart){
        return false;
    }

    public abstract Double calculateCartAmountAfterDiscountApplied(Double amount);

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getThresholdAmount() {
        return thresholdAmount;
    }
}

