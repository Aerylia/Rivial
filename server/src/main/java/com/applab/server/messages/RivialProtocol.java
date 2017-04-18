package com.applab.server.messages;

import com.applab.server.handlers.RivialHandler;

import java.io.Serializable;

/**
 * Created by arian on 9-4-2017.
 */

public abstract class RivialProtocol implements Serializable{

    enum messageType{
        INIT, GET_GAMES, JOIN_GAME, CREATE_GAME, START_GAME, END_GAME
    }

    public abstract messageType getMessageType();
    public abstract RivialHandler getHandler();

}
