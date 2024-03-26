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

    public Dealer getDealer() {
        return dealer;
    }

    // add player to the game
    public void addPlayer(String playerName, int playerChips) {
        Player player = new Player(playerName, playerChips);
        players.add(player);
    }

    // start the game
    public void startGame() {
        for (Player player : players) player.clearHand();
        dealer.clearHand();
        deck = new Deck(6);
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
    public Card dealerHit() {
        int handValue = dealer.getHandTotal();
        if (handValue < 17) {
            Card dealt = deck.dealCard();
            dealer.addCardToHand(dealt);
            if (dealer.getHandTotal() > 21) {
                dealer.getHand().setStatus(Hand.Status.BUST);
            }
            return dealt;
        }
        return null;
    }

    // Player action: Hit - add card to player hand
    public Card hit(Player player) {
        if (player.getHand().getStatus() != Hand.Status.BLACKJACK) {
            List<Card> hand = player.getHand().getCards();
            Card card = deck.dealCard();
            hand.add(card);
            if (player.getHandTotal() > 21) {
                player.getHand().setStatus(Hand.Status.BUST);
            }
            return card;
        }
        return null;
    }

    public void stand(Player player) {
        player.getHand().setStatus(Hand.Status.STOOD);
    }

    public void calculateWin() {
        for (Player player : players) {
            if (player.getHand().getStatus() != Hand.Status.BUST) {
                if (player.getHandTotal() > dealer.getHandTotal()) {
                    player.getHand().setStatus(Hand.Status.WIN);
                    player.addChips(player.getBet() * 2);
                    player.setBet(0);
                } else if (player.getHandTotal() < dealer.getHandTotal()) {
                    player.getHand().setStatus(Hand.Status.LOSE);
                    player.setBet(0);
                } else {
                    player.getHand().setStatus(Hand.Status.PUSH);
                    player.addChips(player.getBet());
                    player.setBet(0);
                }
            } else {
                player.getHand().setStatus(Hand.Status.LOSE);
                player.setBet(0);
            }
        }
    }

    public void endSession() {
        for (Player player : players) player.clearHand();
        dealer.clearHand();
        players.clear();
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
