package com.game.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/game/masterblackjack/start-screen.fxml"));
        stage.setTitle("MasterBlackjack");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/game/masterblackjack/start-screen.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}