package com.applab.server.handlers;

import com.applab.exceptions.GameNotFoundException;
import com.applab.exceptions.PlayerNotFoundException;
import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.CreateGameMessage;

import java.io.IOException;

/**
 * Created by arian on 9-4-2017.
 */

public class CreateGameHandler extends RivialHandler {

    CreateGameMessage message;

    public CreateGameHandler(CreateGameMessage message){
        this.message = message;
    }
    @Override
    public void run() {
        if (serverSide) {
            try {
                GameModel game = server.createGame(message.getPlayer());
                ReplyProtocol replyProtocol = new ReplyProtocol();
                message.addGame(game);
                replyProtocol.addReply(message, clientSocket);
                server.joinGame(message.getPlayer(), message.getGame().getId());
            } catch (PlayerNotFoundException e){
                e.printStackTrace();
            } catch (GameNotFoundException e){
                e.printStackTrace();
            }
        } else {
            client.handleGame(message.getGame());

        }
    }
}
