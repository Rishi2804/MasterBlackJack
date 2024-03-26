package com.game.model;

import java.util.List;

public class Dealer {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public int getHandTotal() {
        List<Card> cardsInHand = hand.getCards();
        int total = 0;
        for (Card card : cardsInHand) {
            total += card.getValue();
        }
        if (total > 21) {
            for (Card card : cardsInHand) {
                if (card.getRank() == Card.Rank.ACE) {
                    total -= 10;
                }
            }
        }
        return total;
    }

    public void addCardToHand(Card card) {
        hand.getCards().add(card);
    }

    public void clearHand() {
        hand.clear();
    }

}