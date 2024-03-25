package com.game.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    public enum Status {
        BUST, WIN, PUSH, BLACKJACK, NONE
    }
    private List<Card> cards;
    private Status status;

    Hand() {
        this.cards = new ArrayList<>();
        status = Status.NONE;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void clear() {
        cards.clear();
        status = Status.NONE;
    }
}
