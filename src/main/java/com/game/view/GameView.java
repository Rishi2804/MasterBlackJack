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
    private HandView dealerHand;

    public GameView(GameController gc) {
        this.gameController = gc;
        this.playerHands = new ArrayList<>();
        this.dealerHand = new HandView(HandView.Position.DEALER);
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
        root.getChildren().add(dealerHand.getHand());
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/game/masterblackjack/game-screen.css").toExternalForm());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public void addNewPlayerHands(String name, String chips, HandView.Position pos) {
        HandView hand = new HandView(name, chips, pos);
        playerHands.add(hand);
        root.getChildren().add(hand.getHand());
    }

    public void addToHand(String cardName, int playerIndex) {
        CardView newCard = new CardView(cardName);
        playerHands.get(playerIndex).addToHand(newCard);
    }

    public void addToDealerHand(String cardName) {
        CardView newCard = new CardView(cardName);
        dealerHand.addToHand(newCard);
    }

}
