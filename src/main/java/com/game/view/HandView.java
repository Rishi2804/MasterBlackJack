package com.game.view;

import javafx.scene.Group;

public class HandView {
    private Group hand;

    HandView() {
        this.hand = new Group();
    }

    public Group getHand() {
        return hand;
    }


    public void addToHand(CardView card) {
        int numCards = hand.getChildren().size();
        double x = card.getCard().getX();
        card.getCard().setX(x + (numCards * 20));
        hand.getChildren().add(card.getCard());
    }
}
