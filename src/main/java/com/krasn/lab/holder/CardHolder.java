package com.krasn.lab.holder;

import com.krasn.lab.factory.CardFactory;
import com.krasn.lab.model.Card;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardHolder {

    private static final List<Card> cards = new LinkedList<>();

    public static void load(final String path) {
        CardFactory.loadCards(path)
                .forEach(CardHolder::registerCard);
    }

    public static Optional<Card> getCardById(final String ID) {
        return cards.stream()
                .filter(card -> card.getID().equals(ID))
                .findFirst();
    }

    public static void registerCard(final Card card) {
        if(cards.stream().anyMatch(c -> c.getID().equals(card.getID()))) {
            return;
        }
        cards.add(card);
    }

}
