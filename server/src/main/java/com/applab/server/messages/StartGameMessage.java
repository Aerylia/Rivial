package com.applab.server.messages;

import com.applab.server.handlers.RivialHandler;
import com.applab.server.handlers.StartGameHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class StartGameMessage extends RivialProtocol {

    private int gameID;
    private int id;

    public StartGameMessage(int userID, int gameID){
        this.id = userID;
        this.gameID = gameID;
    }

    public int getGameID(){
        return this.gameID;
    }

    public int getID(){
        return this.id;
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
