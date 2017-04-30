package com.applab.server.handlers;

import com.applab.exceptions.GameNotFoundException;
import com.applab.exceptions.PlayerNotFoundException;
import com.applab.model.Player;
import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.EndGameMessage;

import java.io.IOException;

/**
 * Created by arian on 9-4-2017.
 */

public class EndGameHandler extends RivialHandler {

    EndGameMessage message;

    public EndGameHandler(EndGameMessage message){
        this.message = message;
    }
    @Override
    public void run(){
        if(serverSide){
            try {
                if (server.isEndGame(message.getGame())) {
                    try {
                        ReplyProtocol reply = new ReplyProtocol();
                        for (Player player : server.getPlayers(message.getGame())) {
                            reply.addReply(message, player.getSocket());
                        }
                        reply.sendReplies();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (GameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (GameNotFoundException e){
                e.printStackTrace();
            }
        } else {
            client.endGame(message.getGame());
        }
    }
}
