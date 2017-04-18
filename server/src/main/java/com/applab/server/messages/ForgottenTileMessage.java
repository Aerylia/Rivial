package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.model.GameTile;
import com.applab.model.Player;
import com.applab.server.handlers.ForgottenTileHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 18-4-2017.
 */

public class ForgottenTileMessage extends RivialProtocol {

    private GameModel game;
    private GameTile tile;
    private Player player;

    public ForgottenTileMessage(GameModel game, GameTile tile, Player player){
        this.game = game;
        this.tile = tile;
        this.player = player;
    }

    public GameModel getGame() {
        return game;
    }

    public GameTile getTile() {
        return tile;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public messageType getMessageType() {
        return messageType.FORGOT_TILE;
    }

    @Override
    public RivialHandler getHandler() {
        return new ForgottenTileHandler(this);
    }
}
