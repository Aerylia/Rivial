package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.CreateGameMessage;

/**
 * Created by arian on 9-4-2017.
 */

public class CreateGameHandler implements RivialHandler {

    CreateGameMessage message;

    public CreateGameHandler(CreateGameMessage message){
        this.message = message;
    }
    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        int game = server.createGame();
        ReplyProtocol replyProtocol = new ReplyProtocol();
        message.addGame(game);
        replyProtocol.addReply(message, server.currentClient);
        server.joinGame(message.getID(), message.getGame()); // TODO extract gameID from getGame()!
        return new ReplyProtocol();
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.handleGame(message.getGame());
        return new ReplyProtocol();
    }
}
