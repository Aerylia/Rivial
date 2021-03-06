package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.handlers.JoinGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class JoinGameMessage extends RivialProtocol {

    private int player;
    private int game;

    public JoinGameMessage(int player, int game){
        this.player = player;
        this.game = game;
    }

    public int getPlayer() {
        return player;
    }

    public int getGame() {
        return game;
    }

    @Override
    public messageType getMessageType() {
        return messageType.JOIN_GAME;
    }

    @Override
    public RivialHandler getHandler() {
        return new JoinGameHandler(this);
    }
}
