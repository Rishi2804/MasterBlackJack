package com.game.controller;

import com.game.model.*;
import com.game.view.GameView;
import javafx.stage.Stage;

import java.util.List;

public class GameController {
    private Table table;
    private GameView view;
    private int playerTurnIndex;
    private int playerHandIndex;
    private boolean gameInProgress;


    public void initialize(Stage stage) {
        table = new Table();
        playerTurnIndex = 0;
        playerHandIndex = 0;
        gameInProgress = false;
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
        playerTurnIndex = 0;
        playerHandIndex = 0;
        gameInProgress = true;
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
        if (gameInProgress) {
            Player currentPlayer = table.getPlayers().get(playerTurnIndex);
            Card card = table.hit(currentPlayer, playerHandIndex);
            view.addToHand(card.getString(), playerTurnIndex, playerHandIndex);
            if (currentPlayer.getHandTotal(playerHandIndex) > 21) {
                // BUST
                nextTurn(currentPlayer);
            }
        }
    }

    public void stand() {
        if (playerTurnIndex != -1) nextTurn(table.getPlayers().get(playerTurnIndex));
    }

    private void nextTurn(Player currentPlayer) {
        if (playerTurnIndex != -1) {
            playerHandIndex++;
            if (playerHandIndex + 1 > currentPlayer.getHands().size()) {
                playerHandIndex = 0;
                playerTurnIndex++;
                if (playerTurnIndex + 1 > table.getPlayers().size()) {
                    // end game
                    playerTurnIndex = -1;
                    playerHandIndex = -1;
                    gameInProgress = false;
                }
            }
        }
    }

}
