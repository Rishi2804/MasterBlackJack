package com.game.model;

import java.util.ArrayList;

public class Dealer {
    private ArrayList<Card> hand;

    public Dealer() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHandTotal() {
        int total = 0;
        for (Card card : hand) {
            total += card.getValue();
        }
        if (total > 21) {
            for (Card card : hand) {
                if (card.getRank() == Card.Rank.ACE) {
                    total -= 10;
                }
            }
        }
        return total;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

}