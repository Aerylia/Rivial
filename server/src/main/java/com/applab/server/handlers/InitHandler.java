package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.InitMessage;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public class InitHandler extends RivialHandler {

    private InitMessage message;

    public InitHandler(InitMessage message){
        this.message = message;
    }
    @Override
    public void run(){
        try{
            if(serverSide){
                ReplyProtocol reply = new ReplyProtocol();
                message.setID(server.addClient(clientSocket));
                reply.addReply(message, clientSocket);
                reply.sendReplies();
            } else {
                client.setID(message.getID());
            }
        } catch (IOException e){
                e.printStackTrace();
        }
    }
}
