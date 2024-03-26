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
        view.startGame();
        table.startGame();
        playerTurnIndex = 0;
        gameInProgress = true;
        // add all the intial player cards to the display
        for (int i = 0; i < table.getPlayers().size(); i++) {
            for (int j = 0; j < table.getPlayers().get(i).getHand().getCards().size(); j++) {
                Card card = table.getPlayers().get(i).getHand().getCards().get(j);
                view.addToHand(card.getString(), i);
            }
            view.setHandStatusText(i, table.getPlayers().get(i).getHand().getStatus());
        }
        // add dealer cards to the display, first face up, second face down
        String firstCard = table.getDealer().getHand().getCards().get(0).getString();
        view.addToDealerHand(firstCard);
        view.addToDealerHand("back_of_card");
    }

    public void hit() {
        if (gameInProgress) {
            Player currentPlayer = table.getPlayers().get(playerTurnIndex);
            if (currentPlayer.getHand().getStatus() == Hand.Status.BLACKJACK) {
                nextTurn();
                hit(); // hit for the next player
            }
            Card card = table.hit(currentPlayer);
            view.addToHand(card.getString(), playerTurnIndex);
            if (currentPlayer.getHand().getStatus() == Hand.Status.BUST) {
                view.setHandStatusText(playerTurnIndex, Hand.Status.BUST);
                nextTurn();
            }
        }
    }

    public void stand() {
        if (gameInProgress) {
            Player currentPlayer = table.getPlayers().get(playerTurnIndex);
            table.stand(currentPlayer);
            view.setHandStatusText(playerTurnIndex, currentPlayer.getHand().getStatus());
            nextTurn();
        }
    }

    private void nextTurn() {
        if (playerTurnIndex != -1) {
            playerTurnIndex++;
            if (playerTurnIndex + 1 > table.getPlayers().size()) {
                // end game
                playerTurnIndex = -1;
                gameInProgress = false;
                dealerPlay();
            }
        }
    }

    private void dealerPlay() {
        String unveilSecondCard = table.getDealer().getHand().getCards().get(1).getString();
        view.unveilDealerCard(unveilSecondCard);
        int handValue = table.getDealer().getHandTotal();
        while (handValue < 17) {
            Card newCard = table.dealerHit();
            view.addToDealerHand(newCard.getString());
            handValue = table.getDealer().getHandTotal();
        }
        view.stopGame();
        table.calculateWin();
        for (int i = 0; i < table.getPlayers().size(); i++) {
            view.setHandStatusText(i, table.getPlayers().get(i).getHand().getStatus());
        }
    }

}
