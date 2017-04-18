package com.applab.server.handlers;

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
                server.joinGame(message.getPlayer(), message.getGame());
                ReplyProtocol reply = new ReplyProtocol();
                for(Player player: server.getPlayers(message.getGame())){
                    reply.addReply(message, player.getSocket());
                }
                reply.sendReplies();
            } else {
                client.playerJoinedGame(message.getPlayer(), message.getGame());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
