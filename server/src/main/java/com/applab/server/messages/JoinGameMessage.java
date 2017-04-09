package com.applab.server.messages;

import com.applab.server.handlers.JoinGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class JoinGameMessage extends RivialProtocol {

    private int id;
    private int gameID;

    public JoinGameMessage(int userID, int gameID){
        this.id = userID;
        this.gameID = gameID;
    }

    public int getGameID(){ return this.gameID; }

    public int getID(){
        return this.id;
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
