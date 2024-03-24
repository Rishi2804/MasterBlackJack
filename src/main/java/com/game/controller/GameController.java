package com.game.controller;

import com.game.model.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class GameController {
    private Table table;
    int playerTurnIndex;
    int playerHandIndex;
    private Stage stage;
    private Scene scene;
    private AnchorPane root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(Stage stage) {
        table = new Table();
        playerTurnIndex = 0;
        playerHandIndex = 0;
        root = new AnchorPane();
        root.setPrefHeight(650.0);
        root.setPrefWidth(1092.5);
        Image tableImage = new Image("/com/game/masterblackjack/images/blackjack_table.JPG");
        ImageView background = new ImageView(tableImage);
        background.setFitHeight(650.0);
        background.setFitWidth(1092.5);
        background.preserveRatioProperty();
        root.getChildren().add(background);
        scene = new Scene(root);
        setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    public void addPlayers(List<String> names, List<String> chips) {
        for (int i = 0; i < names.size(); i++) {
            table.addPlayer(names.get(i), Integer.parseInt(chips.get(i)));
        }
    }

    public void hit() {
        table.hit(table.getPlayers().get(playerTurnIndex), 0);
    }
}
