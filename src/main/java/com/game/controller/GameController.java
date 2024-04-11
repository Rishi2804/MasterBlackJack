package com.game.controller;

import com.game.model.*;
import com.game.view.GameView;
import com.game.view.HandView;
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

    public void addPlayers(List<String> names, List<String> chips, List<Position> positions, List<Boolean> playerType) {
        for (int i = 0; i < names.size(); i++) {
            table.addPlayer(names.get(i), Integer.parseInt(chips.get(i)), playerType.get(i));
            view.addNewPlayerHands(names.get(i), chips.get(i), positions.get(i));
        }
    }

    public void removePlayers(HandView hand) {
        if (view.getPlayerHands().size() > 1) {
            int index = view.getPlayerHands().indexOf(hand);
            table.removePlayer(index);
            view.removePlayerHand(index);
        } else {
            endGame();
        }
    }

    public void startGame() {
        for (int i = 0; i < table.getPlayers().size(); i++) {
            int bet = view.getPlayerHands().get(i).getFieldText(true);
            if (bet == 0) return;
            Player player = table.getPlayers().get(i);
            int chips = player.getChips();
            if (chips == 0) {
                int newChips = view.getPlayerHands().get(i).getFieldText(false);
                if (newChips == 0) return;
                view.getPlayerHands().get(i).setFieldVisible(false, false);
                player.addChips(newChips);
                chips = newChips;
            }
            if (bet > chips) return;
            player.setBet(bet);
            player.removeChips(bet);
            chips = player.getChips();
            view.getPlayerHands().get(i).setNewChipsText(String.valueOf(chips));
        }
        playerTurnIndex = 0;
        boolean showDoubleDown = table.getPlayers().get(playerTurnIndex).canDoubleDown();
        view.startGame(showDoubleDown);
        table.startGame();
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
        if (table.getPlayers().get(0).getHand().getStatus() == Hand.Status.BLACKJACK) {
            nextTurn();
        }
        aiPlayerPlay();
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
            if (currentPlayer.getHand().getStatus() == Hand.Status.BUST ) {
                view.setHandStatusText(playerTurnIndex, Hand.Status.BUST);
                nextTurn();
            } else if (currentPlayer.getHandTotal() == 21) {
                view.setHandStatusText(playerTurnIndex, Hand.Status.STOOD);
                nextTurn();
            } else {
                view.toggleableChange("Double Down", false);

            }
            if (currentPlayer instanceof AIPlayer) {
                AIPlayer temp = (AIPlayer) currentPlayer;
                temp.updateQValueAfterMove("HIT", table);
                aiPlayerPlay(); // go again once hit
            }
        }
    }

    public void doubleDown() {
        Player currentPlayer = table.getPlayers().get(playerTurnIndex);
        if (gameInProgress && currentPlayer.canDoubleDown()) {
            if (currentPlayer.getHand().getStatus() == Hand.Status.BLACKJACK) {
                nextTurn();
                doubleDown(); // doubledown for the next player
            }
            Card card = table.doubleDown(currentPlayer);
            view.addToHand(card.getString(), playerTurnIndex);
            view.getPlayerHands().get(playerTurnIndex).setNewChipsText(String.valueOf(currentPlayer.getChips()));
            if (currentPlayer.getHand().getStatus() == Hand.Status.BUST) {
                view.setHandStatusText(playerTurnIndex, Hand.Status.BUST);
            } else {
                view.setHandStatusText(playerTurnIndex, Hand.Status.DOUBLE_DOWN);
            }
            nextTurn();
        }
    }

    public void aiPlayerPlay() {
        if (playerTurnIndex != -1 && table.getPlayers().get(playerTurnIndex) instanceof AIPlayer) {
            AIPlayer currentPlayer = (AIPlayer) table.getPlayers().get(playerTurnIndex);
            String action = currentPlayer.generateMove(table);
            if (action == "HIT") {
                hit();
            } else if (action == "STAND"){
                stand();
            }
        }
    }

    public void stand() {
        if (gameInProgress) {
            Player currentPlayer = table.getPlayers().get(playerTurnIndex);
            table.stand(currentPlayer);
            if (currentPlayer instanceof AIPlayer) {
                AIPlayer temp = (AIPlayer) currentPlayer;
                temp.updateQValueAfterMove("STAND", table);
            }
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
            } else if (table.getPlayers().get(playerTurnIndex).getHand().getStatus() == Hand.Status.BLACKJACK) {
                nextTurn();
            } else {
                boolean doubledown = table.getPlayers().get(playerTurnIndex).canDoubleDown();
                view.toggleableChange("Double Down", doubledown);
                aiPlayerPlay(); // will only do anything if current player is an AI
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
            int chips = table.getPlayers().get(i).getChips();
            view.getPlayerHands().get(i).setNewChipsText(String.valueOf(chips));
            view.setHandStatusText(i, table.getPlayers().get(i).getHand().getStatus());
            if (chips == 0) {
                view.getPlayerHands().get(i).setFieldVisible(false, true);
            }
        }
    }

    public void endGame() {
        for (Player player : table.getPlayers()) {
            if (player instanceof AIPlayer) {
                AIPlayer temp = (AIPlayer) player;
                temp.saveQTable("qtable.dat");
                break;
            }
        }
        table.endSession();
        view.stopGame();
        view.clearHands();
        gameInProgress = false;
        try{
            view.endGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
