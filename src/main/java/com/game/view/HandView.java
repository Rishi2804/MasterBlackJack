package com.game.view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

import java.util.Iterator;

public class HandView {
    private Group hand;
    private Label statusLabel;
    private Label chipsLabel;
    private TextField betField;
    private TextField chipsField;

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

        // Elements if not dealer
        if (pos != Position.DEALER) {
            // Labels
            Label nameLabel = new Label(name);
            nameLabel.setLayoutY(125.0);
            nameLabel.getStyleClass().add("name-label");
            chipsLabel = new Label("Chips: " + chips);
            chipsLabel.setLayoutY(155.0);
            chipsLabel.getStyleClass().add("chips-label");
            statusLabel = new Label();
            statusLabel.setLayoutY(-40.0);
            statusLabel.getStyleClass().add("name-label");
            hand.getChildren().add(nameLabel);
            hand.getChildren().add(chipsLabel);
            hand.getChildren().add(statusLabel);

            // add bet text field
            betField = new TextField();
            betField.setPromptText("Enter Bet");
            betField.setLayoutY(35.0);
            betField.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
            hand.getChildren().add(betField);

            // add chips text field
            chipsField = new TextField();
            chipsField.setPromptText("Enter Chips");
            chipsField.setVisible(false);
            chipsField.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
            hand.getChildren().add(chipsField);
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

    public void clearHand() {
        // Get the list of children nodes
        ObservableList<Node> children = hand.getChildren();

        // Iterate through the children and remove ImageView nodes
        Iterator<Node> iterator = children.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof ImageView) {
                iterator.remove(); // Remove the ImageView
            }
        }
        if (statusLabel != null) statusLabel.setText("");
    }

    public int getFieldText(boolean bet) {
        TextField field;
        if (bet) {
            field = betField;
        } else {
            field = chipsField;
        }
        if (field.getText().isBlank()) return 0;
        else return Integer.parseInt(field.getText());
    }

    public void setFieldVisible(boolean bet, boolean visible) {
        TextField field;
        if (bet) {
            field = betField;
        } else {
            field = chipsField;
        }
        field.setVisible(visible);
        if (visible) field.toFront();
        else field.setText("");
    }

    public void setNewChipsText(String chips) {
        chipsLabel.setText("Chips: " + chips);
    }

    public void handleNumericInput(KeyEvent event) {
        String input = event.getCharacter();
        if (!isNumeric(input)) {
            event.consume();
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // Matches numeric input including negative numbers and decimals
    }

}
