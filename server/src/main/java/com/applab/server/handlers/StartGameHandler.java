package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetWordMessage;
import com.applab.server.messages.StartGameMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by arian on 9-4-2017.
 */

public class StartGameHandler extends RivialHandler {

    StartGameMessage message;

    public StartGameHandler(StartGameMessage message){
        this.message = message;
    }

    @Override
    public void run() {
        try {
            if (serverSide) {
                ReplyProtocol replyProtocol = new ReplyProtocol();
                ArrayList<Socket> players = server.getPlayers(message.getGameID());
                if (server.startGame(message.getID(), message.getGameID())) {
                    for (Socket player : players) {
                        replyProtocol.addReply(message, player);
                    }
                }
                replyProtocol.sendReplies();
            } else {
                ReplyProtocol replyProtocol = new ReplyProtocol();
                client.startGame();
                replyProtocol.addReply(new GetWordMessage(client.getID()), client.getSocket());
                replyProtocol.sendReplies();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
