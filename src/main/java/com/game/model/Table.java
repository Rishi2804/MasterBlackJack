package com.game.model;

import java.util.ArrayList;
public class Table {
    private Deck deck;
    private ArrayList<Player> players;
    private Dealer dealer;
    private int playerTurnIndex;
    private int playerHandIndex;

    public Table() {
        deck = new Deck();
        players = new ArrayList<>();
        dealer = new Dealer();
        playerTurnIndex = 0;
        playerHandIndex = 0;
    }

    // add player to the game
    public void addPlayer(String playerName, int playerChips) {
        Player player = new Player(playerName, playerChips);
        players.add(player);
    }

    // start the game
    public void startGame() {
        deck.shuffle();
        dealInitialCards();
        playerTurnIndex = 0;
        playerHandIndex = 0;
    }

    // helper for initial dealing of cards
    private void dealInitialCards() {
        for (Player player : players) {
            player.clearHands();
            ArrayList<Card> hand = player.getHand();
            hand.add(deck.dealCard());
            hand.add(deck.dealCard());
        }
        dealer.clearHand();
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());
    }

    // Dealer action: play until hand gets to 17
    // TODO: implement learning ai
    public void dealerPlay() {
        int handValue = dealer.getHandTotal();
        while (handValue > 17) {
            dealer.addCardToHand(deck.dealCard());
            handValue = dealer.getHandTotal();
        }
    }

    // Player action: Hit - add card to player hand
    public void hit(Player player) {
        ArrayList<Card> hand = player.getHand(playerHandIndex);
        hand.add(deck.dealCard());
        int handValue = player.getHandTotal(playerHandIndex);
        if (handValue > 21) {
            // notify bust
            changeTurn();
        }
    }

    // Player action: Stand - move onto the next hand's turn
    public void stand() {
        changeTurn();
    }

    // helper for changing the turn indicies
    private void changeTurn() {
        playerHandIndex++;
        if (playerHandIndex > players.get(playerTurnIndex).getHands().size()) {
            playerHandIndex = 0;
            playerTurnIndex++;
        }
        if (playerTurnIndex > players.size()) {
            playerTurnIndex = -1;
        }
    }

    // helper for confirming whether player can split their hand or not
    public boolean canSplit(Player player) {
        ArrayList<ArrayList<Card>> hands = player.getHands();
        return (hands.size() == 1 && hands.get(0).size() == 2 &&
                hands.get(0).get(0).getRank() == hands.get(0).get(1).getRank());
    }

    // Player action: Split - splits the player's hand and runs two hands simultaneously
    public void split(Player player) {
        if (canSplit(player)) {
            ArrayList<ArrayList<Card>> hands = player.getHands();
            ArrayList<Card> newHand = new ArrayList<>();
            // Remove the second card from the original hand and add it to the new hand
            newHand.add(hands.get(0).remove(1));
            hands.add(newHand);
        }
    }
}
