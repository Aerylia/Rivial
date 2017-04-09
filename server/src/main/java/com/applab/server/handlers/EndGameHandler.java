package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.EndGameMessage;

/**
 * Created by arian on 9-4-2017.
 */

public class EndGameHandler implements RivialHandler {

    EndGameMessage message;

    public EndGameHandler(EndGameMessage message){
        this.message = message;
    }
    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        return new ReplyProtocol();
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.endGame();
        return new ReplyProtocol();
    }
}
