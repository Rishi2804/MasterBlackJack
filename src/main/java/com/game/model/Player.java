package com.game.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<ArrayList<Card>> hands;
    private int chips;
    private int bet;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.hands = new ArrayList<>();
        this.hands.add(new ArrayList<>());
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

    public ArrayList<ArrayList<Card>> getHands() {
        return hands;
    }

    public ArrayList<Card> getHand() {
        return hands.get(0);
    }
    public ArrayList<Card> getHand(int index) {
        return hands.get(index);
    }

    public void addCardToHand(int index, Card card) {
        hands.get(index).add(card);
    }

    public int getHandTotal(int index) {
        ArrayList<Card> hand = getHand(index);
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

    public void clearHand(int index) {
        hands.get(index).clear();
    }

    public void clearHands() {
        for (ArrayList<Card> hand : hands) {
            hand.clear();
        }
    }
}
