package com.game.view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class HandView {
    private Group hand;
    private Label statusLabel;

    public enum Position {
        LEFT, LEFTMIDDLE, CENTRE, RIGHTMIDDLE, RIGHT, DEALER;
    }

    HandView(String name, String chips, Position pos) {
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

        // Labels if not dealer
        if (pos != Position.DEALER) {
            Label nameLabel = new Label(name);
            nameLabel.setLayoutY(125.0);
            nameLabel.getStyleClass().add("name-label");
            Label chipsLabel = new Label("Chips: " + chips);
            chipsLabel.setLayoutY(155.0);
            chipsLabel.getStyleClass().add("chips-label");
            statusLabel = new Label();
            statusLabel.setLayoutY(-40.0);
            statusLabel.getStyleClass().add("name-label");
            hand.getChildren().add(nameLabel);
            hand.getChildren().add(chipsLabel);
            hand.getChildren().add(statusLabel);
        }

        hand.getTransforms().add(rotate);
    }

    HandView(Position pos) {
        if (pos == Position.DEALER) {
            this.hand = new Group();
            hand.setLayoutX(516.0);
            hand.setLayoutY(18.0);
        }
    }

    public Group getHand() {
        return hand;
    }

    public void addToHand(CardView card) {
        int numCards = hand.getChildren().filtered(node -> node instanceof ImageView).size();
        card.getCard().setX(numCards * 20);
        hand.getChildren().add(card.getCard());
    }

    public void setStatusText(String statusText) {
        statusLabel.setText(statusText);
    }
}
