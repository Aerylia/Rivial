package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetGamesMessage;

/**
 * Created by arian on 9-4-2017.
 */

public class GetGamesHandler implements RivialHandler {

    private GetGamesMessage message;

    public GetGamesHandler(GetGamesMessage message){
        this.message = message;
    }
    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        ReplyProtocol reply = new ReplyProtocol();
        message.addGames(server.getGames());
        reply.addReply(message, server.currentClient);
        return reply;
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.handleGames(message.getGames());
        return new ReplyProtocol();
    }
}
