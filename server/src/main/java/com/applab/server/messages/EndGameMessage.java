package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.server.handlers.EndGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class EndGameMessage extends RivialProtocol {

    private GameModel game;

    public EndGameMessage(GameModel game){
        this.game = game;
    }

    public GameModel getGame() {
        return game;
    }

    @Override
    public messageType getMessageType() {
        return messageType.END_GAME;
    }

    @Override
    public RivialHandler getHandler() {
        return new EndGameHandler(this);
    }
}
