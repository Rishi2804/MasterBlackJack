package com.game.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class StartScreenController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void gameViewSwitcher(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/game/masterblackjack/game-screen.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}