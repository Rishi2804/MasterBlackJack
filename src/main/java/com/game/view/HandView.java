package com.game.view;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public class HandView {
    private List<Group> hands;

    HandView() {
        this.hands = new ArrayList<>();
        addHand();
    }

    public List<Group> getHands() {
        return hands;
    }

    public void addHand() {
        Group hand = new Group();
        hands.add(hand);
    }

    public void addToHand(int handIndex, CardView card) {
        int numCards = hands.get(handIndex).getChildren().size();
        double x = card.getCard().getX();
        card.getCard().setX(x + (numCards * 20));
        hands.get(handIndex).getChildren().add(card.getCard());
    }
}
