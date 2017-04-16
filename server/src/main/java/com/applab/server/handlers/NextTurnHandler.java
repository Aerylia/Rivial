package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetWordMessage;
import com.applab.server.messages.NextTurnMessage;

import java.io.IOException;

/**
 * Created by arian on 9-4-2017.
 */

public class NextTurnHandler extends RivialHandler {

    NextTurnMessage message;

    public NextTurnHandler(NextTurnMessage message){
        this.message = message;
    }

    @Override
    public void run(){
        try {
            if (this.serverSide) {

            } else {
                client.nextTurn();
                ReplyProtocol replyProtocol = new ReplyProtocol();
                replyProtocol.addReply(new GetWordMessage(message.getID()), client.getSocket());
                replyProtocol.sendReplies();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
