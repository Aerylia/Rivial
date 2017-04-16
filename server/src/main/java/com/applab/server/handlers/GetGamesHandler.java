package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetGamesMessage;

import java.io.IOException;

/**
 * Created by arian on 9-4-2017.
 */

public class GetGamesHandler extends RivialHandler {

    private GetGamesMessage message;

    public GetGamesHandler(GetGamesMessage message){
        this.message = message;
    }
    @Override
    public void run(){
        try {
            if(serverSide){
                ReplyProtocol reply = new ReplyProtocol();
                message.addGames(server.getGames());
                reply.addReply(message, clientSocket);
                reply.sendReplies();
            } else {
                client.handleGames(message.getGames());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
