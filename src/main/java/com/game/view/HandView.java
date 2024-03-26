package com.game.view;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class HandView {
    private Group hand;

    public enum Position {
        LEFT, LEFTMIDDLE, CENTRE, RIGHTMIDDLE, RIGHT, DEALER;
    }

    HandView(Position pos) {
        this.hand = new Group();
        Rotate rotate = new Rotate();
        rotate.setPivotX(0);
        rotate.setPivotY(121.0);
        switch (pos) {
            case LEFT:
                hand.setLayoutX(42.0);
                hand.setLayoutY(229.0);
                rotate.setAngle(43.0);
                break;
            case LEFTMIDDLE:
                hand.setLayoutX(221.0);
                hand.setLayoutY(393.0);
                rotate.setAngle(21.5);
                break;
            case CENTRE:
                hand.setLayoutX(516.0);
                hand.setLayoutY(461.0);
                break;
            case RIGHTMIDDLE:
                hand.setLayoutX(809.0);
                hand.setLayoutY(423.0);
                rotate.setAngle(-21.5);
                break;
            case RIGHT:
                hand.setLayoutX(1005.0);
                hand.setLayoutY(285.0);
                rotate.setAngle(-43.0);
                break;
            case DEALER:
                hand.setLayoutX(516.0);
                hand.setLayoutY(18.0);
                break;
            default:
                break;
        }
        hand.getTransforms().add(rotate);
    }

    public Group getHand() {
        return hand;
    }


    public void addToHand(CardView card) {
        int numCards = hand.getChildren().size();
        card.getCard().setX(numCards * 20);
        hand.getChildren().add(card.getCard());
    }
}
