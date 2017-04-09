package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.JoinGameMessage;

import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public class JoinGameHandler implements RivialHandler {

    private JoinGameMessage message;

    public JoinGameHandler(JoinGameMessage message){
        this.message = message;
    }

    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        server.joinGame(message.getID(), message.getGameID());
        ReplyProtocol reply = new ReplyProtocol();
        for(Socket player: server.getPlayers(message.getGameID())){
            reply.addReply(message, player);
        }
        return reply;
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.playerJoinedGame(message.getID());
        return new ReplyProtocol();
    }
}
