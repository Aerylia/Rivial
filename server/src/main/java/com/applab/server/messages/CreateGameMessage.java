package com.applab.server.messages;

import com.applab.server.handlers.CreateGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class CreateGameMessage extends RivialProtocol {

    private int game;
    private int id;

    public CreateGameMessage(int id){
        this.id = id;
    }

    public int getID(){ return this.id; }

    public int getGame() {
        return game;
    }

    public void addGame(int game){
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
