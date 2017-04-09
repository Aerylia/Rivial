package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetWordMessage;
import com.applab.server.messages.NextTurnMessage;

/**
 * Created by arian on 9-4-2017.
 */

public class NextTurnHandler implements RivialHandler {

    NextTurnMessage message;

    public NextTurnHandler(NextTurnMessage message){
        this.message = message;
    }

    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        return new ReplyProtocol();
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.nextTurn();
        ReplyProtocol replyProtocol = new ReplyProtocol();
        replyProtocol.addReply(new GetWordMessage(message.getID()), client.getSocket());
        return replyProtocol;
    }
}
