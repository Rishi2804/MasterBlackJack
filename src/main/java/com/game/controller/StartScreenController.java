package com.game.controller;

import com.game.view.HandView.Position;
import javafx.fxml.FXML;
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
    private TextField nameTextField1;
    @FXML
    private TextField numericTextField1;
    @FXML
    private TextField nameTextField2;
    @FXML
    private TextField numericTextField2;
    @FXML
    private TextField nameTextField3;
    @FXML
    private TextField numericTextField3;
    @FXML
    private TextField nameTextField4;
    @FXML
    private TextField numericTextField4;
    @FXML
    private TextField nameTextField5;
    @FXML
    private TextField numericTextField5;
    List<TextField> nameFields;
    List<TextField> numericFields;

    public void initialize() {
        numericTextField1.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField2.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField3.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField4.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericTextField5.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        numericFields = new ArrayList<>();
        numericFields.add(numericTextField1);
        numericFields.add(numericTextField2);
        numericFields.add(numericTextField3);
        numericFields.add(numericTextField4);
        numericFields.add(numericTextField5);
        nameFields = new ArrayList<>();
        nameFields.add(nameTextField1);
        nameFields.add(nameTextField2);
        nameFields.add(nameTextField3);
        nameFields.add(nameTextField4);
        nameFields.add(nameTextField5);

    }

    public void gameViewSwitcher(ActionEvent e) throws IOException {
        GameController gameController = new GameController();

        List<String> names = new ArrayList<>();
        List<String> chips = new ArrayList<>();
        List<Position> positions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            TextField nameField = nameFields.get(i);
            TextField numericField = numericFields.get(i);
            if (!nameField.getText().isBlank() && !numericField.getText().isBlank()) {
                names.add(nameField.getText());
                chips.add(numericField.getText());
                positions.add(Position.values()[i]);
            }
        }

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        if (names.isEmpty()) {
            return;
        }
        gameController.initialize(stage);
        gameController.addPlayers(names, chips, positions);
        gameController.startGame();
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

    public void handleToggleAction(ActionEvent e) {
        ToggleButton button = (ToggleButton) e.getSource();
        if (button.isSelected()) {
            button.setText("AI Player");
        } else {
            button.setText("Human Player");
        }
    }
}