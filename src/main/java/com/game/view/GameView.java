package com.game.view;

import com.game.controller.GameController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    GameController gameController;
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private List<HandView> playerHands;

    public GameView(GameController gc) {
        this.gameController = gc;
        this.playerHands = new ArrayList<>();
    }

    public void startupScreen(Stage stage) {
        // set background
        root = new AnchorPane();
        root.setPrefHeight(650.0);
        root.setPrefWidth(1092.5);
        Image tableImage = new Image("/com/game/masterblackjack/images/blackjack_table.JPG");
        ImageView background = new ImageView(tableImage);
        background.setFitHeight(650.0);
        background.setFitWidth(1092.5);
        background.preserveRatioProperty();

        // set buttons
        Group btngroup = new Group();
        Button hitBtn = new Button("Hit");
        hitBtn.setLayoutX(890.0);
        hitBtn.setLayoutY(563.0);
        hitBtn.setOnAction(actionEvent -> {gameController.hit();});
        btngroup.getChildren().add(hitBtn);

        root.getChildren().add(background);
        root.getChildren().add(btngroup);
        scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public void addNewPlayerHands() {
        HandView hand = new HandView();
        playerHands.add(hand);
        root.getChildren().add(hand.getHands().get(0));
    }

    public void addToHand(String cardName, int playerIndex, int handIndex) {
        CardView newCard = new CardView(cardName);
        newCard.getCard().setX(516.0);
        newCard.getCard().setY(461.0);
        playerHands.get(playerIndex).addToHand(handIndex, newCard);
    }

}
