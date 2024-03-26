package com.game.controller;

import com.game.model.*;
import com.game.view.GameView;
import com.game.view.HandView.Position;
import javafx.stage.Stage;

import java.util.List;

public class GameController {
    private Table table;
    private GameView view;
    private int playerTurnIndex;
    private boolean gameInProgress;


    public void initialize(Stage stage) {
        table = new Table();
        playerTurnIndex = 0;
        gameInProgress = false;
        view = new GameView(this);
        view.startupScreen(stage);
    }

    public void addPlayers(List<String> names, List<String> chips, List<Position> positions) {
        for (int i = 0; i < names.size(); i++) {
            table.addPlayer(names.get(i), Integer.parseInt(chips.get(i)));
            view.addNewPlayerHands(names.get(i), chips.get(i), positions.get(i));
        }
    }

    public void startGame() {
        table.startGame();
        playerTurnIndex = 0;
        gameInProgress = true;
        // add all the intial player cards to the display
        for (int i = 0; i < table.getPlayers().size(); i++) {
            for (int j = 0; j < table.getPlayers().get(i).getHand().getCards().size(); j++) {
                Card card = table.getPlayers().get(i).getHand().getCards().get(j);
                view.addToHand(card.getString(), i);
            }
        }
        // add dealer cards to the display, first face up, second face down
        String firstCard = table.getDealer().getHand().get(0).getString();
        view.addToDealerHand(firstCard);
        view.addToDealerHand("back_of_card");
    }

    public void hit() {
        if (gameInProgress) {
            Player currentPlayer = table.getPlayers().get(playerTurnIndex);
            Card card = table.hit(currentPlayer);
            view.addToHand(card.getString(), playerTurnIndex);
            if (currentPlayer.getHandTotal() > 21) {
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
            playerTurnIndex++;
            if (playerTurnIndex + 1 > table.getPlayers().size()) {
                // end game
                playerTurnIndex = -1;
                gameInProgress = false;
            }
        }
    }

}
