package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetWordMessage;
import com.applab.server.messages.StartGameMessage;

import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public class StartGameHandler implements RivialHandler {

    StartGameMessage message;

    public StartGameHandler(StartGameMessage message){
        this.message = message;
    }

    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        ReplyProtocol replyProtocol = new ReplyProtocol();
        Socket[] players = server.getPlayers(message.getGameID());
        if (server.startGame(message.getID(), message.getGameID())) {
            for (Socket player : players) {
                replyProtocol.addReply(message, player);
            }
        }
        return replyProtocol;
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        ReplyProtocol replyProtocol = new ReplyProtocol();
        client.startGame();
        replyProtocol.addReply(new GetWordMessage(client.getID()), client.getSocket());
        return replyProtocol;
    }
}
