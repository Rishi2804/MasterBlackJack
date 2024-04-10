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
    @FXML
    private ToggleButton toggleButton1;
    @FXML
    private ToggleButton toggleButton2;
    @FXML
    private ToggleButton toggleButton3;
    @FXML
    private ToggleButton toggleButton4;
    @FXML
    private ToggleButton toggleButton5;
    List<TextField> nameFields;
    List<TextField> numericFields;
    List<ToggleButton> toggleButtons;
    List<Number> aiIndecies = new ArrayList<>();

    public void initialize() {
        numericFields = new ArrayList<>();
        numericFields.add(numericTextField1);
        numericFields.add(numericTextField2);
        numericFields.add(numericTextField3);
        numericFields.add(numericTextField4);
        numericFields.add(numericTextField5);
        for (TextField nf : numericFields) {
            nf.addEventFilter(KeyEvent.KEY_TYPED, this::handleNumericInput);
        }

        nameFields = new ArrayList<>();
        nameFields.add(nameTextField1);
        nameFields.add(nameTextField2);
        nameFields.add(nameTextField3);
        nameFields.add(nameTextField4);
        nameFields.add(nameTextField5);

        toggleButtons = new ArrayList<>();
        toggleButtons.add(toggleButton1);
        toggleButtons.add(toggleButton2);
        toggleButtons.add(toggleButton3);
        toggleButtons.add(toggleButton4);
        toggleButtons.add(toggleButton5);
        for (ToggleButton tb : toggleButtons) {
            tb.setOnAction(actionEvent -> {
                int num = toggleButtons.indexOf(tb);
                if (tb.isSelected()) {
                    System.out.println("Toggle button is selected");
                    aiIndecies.add(num);
                } else {
                    System.out.println("Toggle button is deselected");
                    aiIndecies.remove(Integer.valueOf(num));
                }
            });
        }
    }

    public void gameViewSwitcher(ActionEvent e) throws IOException {
        GameController gameController = new GameController();

        List<String> names = new ArrayList<>();
        List<String> chips = new ArrayList<>();
        List<Position> positions = new ArrayList<>();
        List<Boolean> playerType = new ArrayList<>();

        for (Number b : aiIndecies) {
            System.out.println(b);
        }

        for (int i = 0; i < 5; i++) {
            TextField nameField = nameFields.get(i);
            TextField numericField = numericFields.get(i);
            if (!nameField.getText().isBlank() && !numericField.getText().isBlank()) {
                names.add(nameField.getText());
                chips.add(numericField.getText());
                positions.add(Position.values()[i]);
                playerType.add(!aiIndecies.contains(Integer.valueOf(i)));
            }
        }

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        if (names.isEmpty()) {
            return;
        }
        gameController.initialize(stage);
        gameController.addPlayers(names, chips, positions, playerType);
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