package com.applab.model;

import com.applab.exceptions.PlayerNotFoundException;
import com.applab.exceptions.TileNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arian on 9-4-2017.
 */

public class GameModel implements Serializable {

    private int id;
    private ArrayList<GameTile> map;
    private ArrayList<Player> players;
    private Player owningPlayer;

    public GameModel(WordList words, int id, Player owningPlayer) {
        this.id = id;
        this.players = new ArrayList<>();
        this.generateMap(words);
        this.owningPlayer = owningPlayer;
    }

    private void generateMap(WordList words) {
        this.map = new ArrayList<>();
        for (int i = 0; i < words.getItemCount(); i++) {
            map.add(new GameTile(words.getItem(i), i));
        }
    }

    public boolean addPlayer(Player player) {
        if (this.canAddPlayer()) {
            this.players.add(player);
        }
        return false;
    }

    public boolean canAddPlayer() {
        return players.size() < 3;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private GameTile getTileWithId(int id) throws TileNotFoundException {
        for (GameTile tile : this.map) {
            if (tile.getId() == id) {
                return tile;
            }
        }
        throw new TileNotFoundException();
    }

    public boolean canStartGame(Player player) {
        return this.owningPlayer.equals(player) && players.size() == 3;
    }

    public boolean isEndGame() {
        // TODO implement check if end game!
        return false;
    }

    public void tileCaptured(int tile, int player) throws TileNotFoundException, PlayerNotFoundException {
        changeTile(tile, player, true);
    }

    public void tileForgotten(int tile, int player) throws TileNotFoundException, PlayerNotFoundException {
        changeTile(tile, player, false);
    }

    private void changeTile(int tile, int playerId, boolean isOwned) throws TileNotFoundException, PlayerNotFoundException {
        GameTile gameTile = this.getTileWithId(tile);
        Player player = this.getPlayerById(playerId);
        switch (player.getColor()) {
            case Player.BLUE:
                gameTile.setOwnedByBlue(isOwned);
                break;
            case Player.RED:
                gameTile.setOwnedByRed(isOwned);
                break;
            case Player.YELLOW:
                gameTile.setOwnedByYellow(isOwned);
                break;
        }
    }

    private Player getPlayerById(int id) throws PlayerNotFoundException{
        for(Player player : players){
            if(player.getId() == id){
                return player;
            }
        }
        throw new PlayerNotFoundException();
    }

}