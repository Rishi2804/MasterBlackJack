package com.game.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AIPlayer extends Player {
    private static final double LEARNING_RATE = 0.1;
    private static final double DISCOUNT_FACTOR = 0.9;
    private static final double EXPLORATION_RATE = 0.1;

    private enum Action {
        HIT,
        STAND
    }

    private Map<StateActionPair, Double> qTable;

    public AIPlayer(String name, int chips) {
        super(name, chips);
        qTable = new HashMap<>();
    }

    public String generateMove(Table table) {
        State currentState = getState(table);
        Action action = chooseAction(currentState);
        return action.toString();
    }

    public void updateQValueAfterMove(String action, Table table) {
        State currentState = getState(table);
        State nextState = getState(table);
        double reward = calculateReward(table);
        updateQValue(currentState, Action.valueOf(action), nextState, reward);
    }

    private State getState(Table table) {
        // Represent the state as the player's hand total and the dealer's visible card
        int playerHandTotal = getHandTotal();
        int dealerVisibleCardValue = table.getDealer().getHand().getCards().get(0).getValue();
        return new State(playerHandTotal, dealerVisibleCardValue);
    }

    private Action chooseAction(State state) {
        // Epsilon-greedy strategy
        Random random = new Random();
        if (random.nextDouble() < EXPLORATION_RATE) {
            // Explore: choose a random action
            return (random.nextDouble() < 0.5) ? Action.HIT : Action.STAND;
        } else {
            // Exploit: choose action with highest Q-value for the current state
            double hitValue = qTable.getOrDefault(new StateActionPair(state, Action.HIT), 0.0);
            double standValue = qTable.getOrDefault(new StateActionPair(state, Action.STAND), 0.0);

            if (hitValue == 0.0 && standValue == 0.0) {
                return (state.playerHandTotal < 17) ? Action.HIT : Action.STAND;
            }

            return (hitValue > standValue) ? Action.HIT : Action.STAND;
        }
    }

    private double calculateReward(Table table) {
        int playerHandTotal = getHandTotal();
        int dealerHandTotal = table.getDealer().getHandTotal();

        if (playerHandTotal > 21 || (dealerHandTotal <= 21 && dealerHandTotal > playerHandTotal))
            return -1.0; // Player lost
        else if (playerHandTotal == dealerHandTotal)
            return 0.0; // Push
        else if (dealerHandTotal > 21 || playerHandTotal > dealerHandTotal)
            return 1.0; // Player won
        else
            return 0.0; // Shouldn't happen, but just to cover all cases
    }

    private void updateQValue(State currentState, Action action, State nextState, double reward) {
        double currentQValue = qTable.getOrDefault(new StateActionPair(currentState, action), 0.0);
        double maxNextQValue = Math.max(
                qTable.getOrDefault(new StateActionPair(nextState, Action.HIT), 0.0),
                qTable.getOrDefault(new StateActionPair(nextState, Action.STAND), 0.0)
        );
        double updatedQValue = currentQValue + LEARNING_RATE * (reward + DISCOUNT_FACTOR * maxNextQValue - currentQValue);
        qTable.put(new StateActionPair(currentState, action), updatedQValue);
    }

    // Define a class to represent states of the game
    private static class State {
        private int playerHandTotal;
        private int dealerVisibleCardValue;

        public State(int playerHandTotal, int dealerVisibleCardValue) {
            this.playerHandTotal = playerHandTotal;
            this.dealerVisibleCardValue = dealerVisibleCardValue;
        }

        // Implement hashCode and equals methods
        @Override
        public int hashCode() {
            return 31 * playerHandTotal + dealerVisibleCardValue;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State state = (State) obj;
            return playerHandTotal == state.playerHandTotal && dealerVisibleCardValue == state.dealerVisibleCardValue;
        }
    }

    // Define a class to represent state-action pairs
    private static class StateActionPair {
        private State state;
        private Action action;

        public StateActionPair(State state, Action action) {
            this.state = state;
            this.action = action;
        }

        // Implement hashCode and equals methods
        @Override
        public int hashCode() {
            return 31 * state.hashCode() + action.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            StateActionPair pair = (StateActionPair) obj;
            return state.equals(pair.state) && action == pair.action;
        }
    }
}