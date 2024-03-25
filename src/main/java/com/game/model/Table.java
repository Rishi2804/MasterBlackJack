package com.game.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private Deck deck;
    private List<Player> players;
    private Dealer dealer;

    public Table() {
        deck = new Deck(6);
        players = new ArrayList<>();
        dealer = new Dealer();
    }

    public List<Player> getPlayers() {
        return players;
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
    }

    // helper for initial dealing of cards
    private void dealInitialCards() {
        for (Player player : players) {
            player.clearHand();
            List<Card> hand = player.getHand().getCards();
            hand.add(deck.dealCard());
            hand.add(deck.dealCard());
            if (player.getHandTotal() == 21) {
                player.getHand().setStatus(Hand.Status.BLACKJACK);
            }
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
    public Card hit(Player player) {
        List<Card> hand = player.getHand().getCards();
        Card card = deck.dealCard();
        hand.add(card);
        return card;
    }

    // helper for confirming whether player can split their hand or not
//    public boolean canSplit(Player player) {
//        ArrayList<ArrayList<Card>> hands = player.getHands();
//        return (hands.size() == 1 && hands.get(0).size() == 2 &&
//                hands.get(0).get(0).getRank() == hands.get(0).get(1).getRank());
//    }
//
//    // Player action: Split - splits the player's hand and runs two hands simultaneously
//    public void split(Player player) {
//        if (canSplit(player)) {
//            ArrayList<ArrayList<Card>> hands = player.getHands();
//            ArrayList<Card> newHand = new ArrayList<>();
//            // Remove the second card from the original hand and add it to the new hand
//            newHand.add(hands.get(0).remove(1));
//            hands.add(newHand);
//        }
//    }
}
