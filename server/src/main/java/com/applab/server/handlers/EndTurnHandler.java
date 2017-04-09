package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.EndGameMessage;
import com.applab.server.messages.EndTurnMessage;
import com.applab.server.messages.NextTurnMessage;

import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public class EndTurnHandler implements RivialHandler {

    private EndTurnMessage message;

    public EndTurnHandler(EndTurnMessage message){
        this.message = message;
    }

    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        ReplyProtocol replyProtocol = new ReplyProtocol();
        message.setCorrect(server.endTurn(message.getID(), message.getAnswer()));
        replyProtocol.addReply(message, server.currentClient);
        if(server.checkAllEndTurn(message.getGameID())){
            Socket[] players = server.getPlayers(message.getGameID());
            for(Socket player: players){
                if(server.checkEndGame(message.getGameID())){
                    replyProtocol.addReply(new EndGameMessage(), player);
                } else {
                    replyProtocol.addReply(new NextTurnMessage(message.getID()), player);
                }
            }
        }
        return replyProtocol;
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.answerCorrect(message.isCorrect());
        return new ReplyProtocol();
    }
}
