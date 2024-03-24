package com.game.controller;

import com.game.model.*;
import com.game.view.GameView;
import javafx.stage.Stage;

import java.util.List;

public class GameController {
    private Table table;
    private GameView view;
    int playerTurnIndex;
    int playerHandIndex;


    public void initialize(Stage stage) {
        table = new Table();
        playerTurnIndex = 0;
        playerHandIndex = 0;
        view = new GameView(this);
        view.startupScreen(stage);
    }

    public void addPlayers(List<String> names, List<String> chips) {
        for (int i = 0; i < names.size(); i++) {
            table.addPlayer(names.get(i), Integer.parseInt(chips.get(i)));
            view.addNewPlayerHands();
        }
    }

    public void startGame() {
        table.startGame();

        // add all the intial cards to the display
        for (int i = 0; i < table.getPlayers().size(); i++) {
            for (int j = 0; j < table.getPlayers().get(i).getHands().size(); j++) {
                for (int k = 0; k < table.getPlayers().get(i).getHand(j).size(); k++) {
                    Card card = table.getPlayers().get(i).getHand(j).get(k);
                    view.addToHand(card.getString(), i, j);
                }
            }
        }
    }



    public void hit() {
        Card card = table.hit(table.getPlayers().get(playerTurnIndex), 0);
        view.addToHand(card.getString(), playerTurnIndex, playerHandIndex);
    }
}
