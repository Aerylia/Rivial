package com.applab.server.handlers;

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
                server.joinGame(message.getID(), message.getGameID());
                ReplyProtocol reply = new ReplyProtocol();
                for(Socket player: server.getPlayers(message.getGameID())){
                    reply.addReply(message, player);
                }
                reply.sendReplies();
            } else {
                client.playerJoinedGame(message.getID());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
