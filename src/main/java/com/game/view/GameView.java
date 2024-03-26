package com.game.view;

import com.game.controller.GameController;
import com.game.model.Hand.Status;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameView {
    GameController gameController;
    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private List<HandView> playerHands;
    private HandView dealerHand;
    private List<Control> toggleables;

    public GameView(GameController gc) {
        this.gameController = gc;
        this.playerHands = new ArrayList<>();
        this.dealerHand = new HandView(HandView.Position.DEALER);
        this.toggleables = new ArrayList<>();
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
        Group btnGroup = new Group();
        btnGroup.setLayoutX(890.0);
        btnGroup.setLayoutY(563.0);

        // start game button
        Button startBtn = new Button("Start Game");
        startBtn.setOnAction(actionEvent -> gameController.startGame());

        // Plyer action buttons
        Button hitBtn = new Button("Hit");
        hitBtn.setOnAction(actionEvent -> gameController.hit());
        hitBtn.setVisible(false);
        Button standBtn = new Button("Stand");
        standBtn.setLayoutX(70.0);
        standBtn.setId("stand-button");
        standBtn.setVisible(false);
        standBtn.setOnAction(actionEvent -> gameController.stand());

        // add buttons
        toggleables.add(startBtn);
        btnGroup.getChildren().add(startBtn);
        toggleables.add(hitBtn);
        btnGroup.getChildren().add(hitBtn);
        toggleables.add(standBtn);
        btnGroup.getChildren().add(standBtn);

        root.getChildren().add(background);
        root.getChildren().add(btnGroup);
        root.getChildren().add(dealerHand.getHand());
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/game/masterblackjack/game-screen.css").toExternalForm());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public void startGame() {
        Button startBtn = toggleables.stream()
                    .filter(control -> control instanceof Button)
                    .map(control -> (Button) control)
                    .filter(button -> (button.getText() == "Start Game"))
                    .findFirst()
                    .orElse(null);
        if (startBtn != null) startBtn.setVisible(false);
        List<Button> playerActionButtons = toggleables.stream()
                .filter(control -> control instanceof Button)
                .map(control -> (Button) control)
                .filter(button -> (button.getText() == "Hit" || button.getText() == "Stand"))
                .collect(Collectors.toList());
        for (Button btn : playerActionButtons) btn.setVisible(true);
    }

    public void addNewPlayerHands(String name, String chips, HandView.Position pos) {
        HandView hand = new HandView(name, chips, pos);
        playerHands.add(hand);
        root.getChildren().add(hand.getHand());
    }

    public void setHandStatusText(int index, Status status) {
        if (status != Status.NONE) playerHands.get(index).setStatusText(status.toString());
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
