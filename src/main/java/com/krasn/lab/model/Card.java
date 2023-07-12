package com.krasn.lab.model;

import com.krasn.lab.exception.InvalidTransactionException;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private final String ID;
    private double amount;

    public void send(final double amount, final Card card) {
        if(amount > this.amount) {
            throw new InvalidTransactionException(String.format("Cannot send %f dollars from %s to %s", amount, this, card));
        }

        if(amount < 0) {
            throw new InvalidTransactionException(String.format("Amount cannot be negative [%f]", amount));
        }

        card.setAmount(card.getAmount() + amount);
        this.amount -= amount;
    }
}
