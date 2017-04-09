package com.applab.server.messages;

import com.applab.server.handlers.EndGameHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class EndGameMessage extends RivialProtocol {
    @Override
    public messageType getMessageType() {
        return messageType.END_GAME;
    }

    @Override
    public RivialHandler getHandler() {
        return new EndGameHandler(this);
    }
}
