package com.krasn.lab.model;

import com.krasn.lab.controller.OutputController;
import com.krasn.lab.exception.InvalidTransactionException;
import com.krasn.lab.holder.CardHolder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class Transaction {
    private String file;
    private String from;
    private String to;
    private double amount;

    public void perform() {
        final Optional<Card> cardFrom = CardHolder.getCardById(from);
        final Optional<Card> cardTo = CardHolder.getCardById(to);

        if(cardFrom.isEmpty()) {
            OutputController.error(file, String.format("Card with ID=[%s] doesn't exist", from));
            return;
        }

        if(cardTo.isEmpty()) {
            OutputController.error(file, String.format("Card with ID=[%s] doesn't exist", to));
            return;
        }

        try {
            cardFrom.get().send(amount, cardTo.get());
            OutputController.success(file, String.format("%f dollars sent from %s to %s", amount,
                    cardFrom.get(),
                    cardTo.get()));
        } catch (InvalidTransactionException e) {
            OutputController.error(file, e.getError());
        }
    }
}
