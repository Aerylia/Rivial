package com.applab.server.messages;

import com.applab.server.handlers.InitHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class InitMessage extends RivialProtocol {

    private int id;

    public void setID(int id){
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    @Override
    public messageType getMessageType() {
        return messageType.INIT;
    }

    @Override
    public RivialHandler getHandler() {
        return new InitHandler(this);
    }

}
