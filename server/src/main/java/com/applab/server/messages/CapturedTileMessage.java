package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.model.GameTile;
import com.applab.model.Player;
import com.applab.server.handlers.CapturedTileHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 18-4-2017.
 */

public class CapturedTileMessage extends RivialProtocol {

    private int gameId;
    private int tileId;
    private int playerId;

    public CapturedTileMessage(int gameId, int tileId, int playerId){
        this.gameId = gameId;
        this.tileId = tileId;
        this.playerId = playerId;
    }

    public int getGame() {
        return gameId;
    }

    public int getTile() {
        return tileId;
    }

    public int getPlayer() {
        return playerId;
    }

    @Override
    public messageType getMessageType() {
        return messageType.CAPTURED_TILE;
    }

    @Override
    public RivialHandler getHandler() {
        return new CapturedTileHandler(this);
    }
}
