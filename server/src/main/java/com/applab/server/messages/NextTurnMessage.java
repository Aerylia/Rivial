package com.applab.server.messages;

import com.applab.server.handlers.NextTurnHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class NextTurnMessage extends RivialProtocol {

    private int id;

    public NextTurnMessage(int id){
        this.id = id;
    }

    public int getID(){ return this.id; }

    @Override
    public messageType getMessageType() {
        return messageType.NEXT_TURN;
    }

    @Override
    public RivialHandler getHandler() {
        return new NextTurnHandler(this);
    }
}
