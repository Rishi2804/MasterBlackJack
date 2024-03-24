package com.game.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartScreenController {
    private Stage stage;
    private Scene scene;
    public Parent root;

    @FXML
    private TextField numericTextField1;
    @FXML
    private TextField numericTextField2;
    @FXML
    private TextField nameTextField3;
    @FXML
    private TextField numericTextField3;
    @FXML
    private TextField numericTextField4;
    @FXML
    private TextField numericTextField5;

    public void initialize() {
        numericTextField1.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField2.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField3.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField4.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField5.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
    }

    public void gameViewSwitcher(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/game/masterblackjack/game-screen.fxml"));
        root = loader.load();
        GameController gameController = loader.getController();

        List<String> names = new ArrayList<>();
        List<String> chips = new ArrayList<>();
        names.add(nameTextField3.getText());
        chips.add(numericTextField3.getText());

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        gameController.initialize(stage);
        gameController.addPlayers(names, chips);
//        scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("/com/game/masterblackjack/game-screen.css").toExternalForm());
//        stage.setScene(scene);
//        stage.show();
    }

    public void handleNumericInput(KeyEvent event) {
        TextField source = (TextField) event.getSource();
        String input = event.getCharacter();
        if (!isNumeric(input)) {
            event.consume();
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // Matches numeric input including negative numbers and decimals
    }

    public void handleToggleAction(ActionEvent e) {
        ToggleButton button = (ToggleButton) e.getSource();
        if (button.isSelected()) {
            button.setText("AI Player");
        } else {
            button.setText("Human Player");
        }
    }
}