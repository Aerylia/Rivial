package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.handlers.JoinGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class JoinGameMessage extends RivialProtocol {

    private Player player;
    private GameModel game;

    public JoinGameMessage(Player player, GameModel game){
        this.player = player;
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public GameModel getGame() {
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
