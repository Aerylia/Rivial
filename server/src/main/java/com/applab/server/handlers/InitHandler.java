package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.InitMessage;

import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public class InitHandler implements RivialHandler {

    private InitMessage message;

    public InitHandler(InitMessage message){
        this.message = message;
    }
    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        Socket client = server.currentClient;
        ReplyProtocol reply = new ReplyProtocol();
        message.setID(server.addClient(client));
        reply.addReply(message, client);
        return reply;
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.setID(message.getID());
        return new ReplyProtocol();
    }
}
