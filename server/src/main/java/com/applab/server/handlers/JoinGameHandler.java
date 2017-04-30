package com.applab.server.handlers;

import com.applab.exceptions.GameNotFoundException;
import com.applab.exceptions.PlayerNotFoundException;
import com.applab.model.Player;
import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.JoinGameMessage;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public class JoinGameHandler extends RivialHandler {

    private JoinGameMessage message;

    public JoinGameHandler(JoinGameMessage message){
        this.message = message;
    }

    @Override
    public void run(){
        try {
            if (serverSide) {
                try {
                    server.joinGame(message.getPlayer(), message.getGame());
                    ReplyProtocol reply = new ReplyProtocol();
                    for (Player player : server.getPlayers(message.getGame())) {
                        reply.addReply(message, player.getSocket());
                    }
                    reply.sendReplies();
                } catch (GameNotFoundException e){
                    e.printStackTrace();
                } catch (PlayerNotFoundException e){
                    e.printStackTrace();
                }
            } else {
                client.playerJoinedGame(message.getPlayer(), message.getGame());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
