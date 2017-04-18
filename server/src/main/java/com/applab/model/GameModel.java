package com.applab.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arian on 9-4-2017.
 */

public class GameModel implements Serializable{

    private int id;
    private ArrayList<GameTile> map;
    private ArrayList<Player> players;
    private Player owningPlayer;

    public GameModel(ArrayList<String> words, ArrayList<String> translations, int id, Player owningPlayer){
        this.id = id;
        this.players = new ArrayList<>();
        this.generateMap(words, translations);
        this.owningPlayer = owningPlayer;
    }

    private void generateMap(ArrayList<String> words, ArrayList<String> translations) {
        this.map = new ArrayList<>();
        for(int i = 0; i< words.size(); i++){
            map.add(new GameTile(words.get(i), translations.get(i), i));
        }
    }

    public boolean addPlayer(Player player){
        if(this.canAddPlayer()){
            this.players.add(player);
        }
        return false;
    }

    public boolean canAddPlayer(){
        return players.size() < 3;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean canStartGame(Player player) {
        return this.owningPlayer.equals(player) && players.size() == 3;
    }

    public boolean isEndGame() {
        // TODO implement check if end game!
        return false;
    }
}
