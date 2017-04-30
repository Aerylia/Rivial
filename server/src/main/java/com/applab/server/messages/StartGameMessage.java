package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.handlers.RivialHandler;
import com.applab.server.handlers.StartGameHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class StartGameMessage extends RivialProtocol {

    private int game;
    private int player;
    private boolean started;

    public StartGameMessage(int game, int player){
        this.game = game;
        this.player = player;
    }

    public int getGame() {
        return game;
    }

    public int getPlayer() {
        return player;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public messageType getMessageType() {
        return messageType.START_GAME;
    }

    @Override
    public RivialHandler getHandler() {
        return new StartGameHandler(this);
    }
}
