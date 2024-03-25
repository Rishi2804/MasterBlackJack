package com.game.model;

import java.util.List;

public class Player {
    private String name;
    private Hand hand;
    private int chips;
    private int bet;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    public void removeChips(int amount) {
        chips -= amount;
    }

    public Hand getHand() {
        return hand;
    }

    public void addCardToHand(Card card) {
        hand.getCards().add(card);
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

    public void clearHand() {
        hand.clear();
    }
}
