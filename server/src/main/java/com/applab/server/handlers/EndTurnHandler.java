package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.EndGameMessage;
import com.applab.server.messages.EndTurnMessage;
import com.applab.server.messages.NextTurnMessage;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by arian on 9-4-2017.
 */

public class EndTurnHandler extends RivialHandler {

    private EndTurnMessage message;

    public EndTurnHandler(EndTurnMessage message){
        this.message = message;
    }

    @Override
    public void run(){
        try{
            if(serverSide){
                ReplyProtocol replyProtocol = new ReplyProtocol();
                message.setCorrect(server.endTurn(message.getID(), message.getAnswer()));
                replyProtocol.addReply(message, clientSocket);
                if(server.checkAllEndTurn(message.getGameID())){
                    ArrayList<Socket> players = server.getPlayers(message.getGameID());
                    for(Socket player: players){
                        if(server.checkEndGame(message.getGameID())){
                            replyProtocol.addReply(new EndGameMessage(), player);
                        } else {
                            replyProtocol.addReply(new NextTurnMessage(message.getID()), player);
                        }
                    }
                }
                replyProtocol.sendReplies();
            } else {
                client.answerCorrect(message.isCorrect());
            }
        } catch (IOException e ){
            e.printStackTrace();
        }
    }
}
