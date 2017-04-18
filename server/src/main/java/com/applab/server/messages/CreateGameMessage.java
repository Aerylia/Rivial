package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.handlers.CreateGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class CreateGameMessage extends RivialProtocol {

    private GameModel game;
    private int player;

    public CreateGameMessage(int player){
        this.player = player;
    }

    public int getPlayer(){ return this.player; }

    public GameModel getGame() {
        return game;
    }

    public void addGame(GameModel game){
        this.game = game;
    }

    @Override
    public messageType getMessageType() {
        return messageType.CREATE_GAME;
    }

    @Override
    public RivialHandler getHandler() {
        return new CreateGameHandler(this);
    }
}
