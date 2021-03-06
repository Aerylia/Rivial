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

    private int game;
    private int tile;
    private int player;

    public ForgottenTileMessage(int game, int tile, int player){
        this.game = game;
        this.tile = tile;
        this.player = player;
    }

    public int getGame() {
        return game;
    }

    public int getTile() {
        return tile;
    }

    public int getPlayer() {
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
