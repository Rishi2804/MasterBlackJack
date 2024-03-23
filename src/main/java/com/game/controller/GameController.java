package com.game.controller;

import com.game.model.*;

import java.util.List;

public class GameController {
    private Table table;
    int playerTurnIndex;
    int playerHandIndex;

    public void initialize() {
        table = new Table();
        playerTurnIndex = 0;
        playerHandIndex = 0;
    }

    public void addPlayers(List<String> names, List<String> chips) {
        for (int i = 0; i < names.size(); i++) {
            table.addPlayer(names.get(i), Integer.parseInt(chips.get(i)));
        }
    }

    public void hit() {
        table.hit(table.getPlayers().get(playerTurnIndex), 0);
    }
}
