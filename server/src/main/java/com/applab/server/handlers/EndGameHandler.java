package com.applab.server.handlers;

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

        } else {
            client.endGame();
        }
    }
}
