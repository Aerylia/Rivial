package com.applab.server.messages;

import com.applab.server.handlers.EndTurnHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class EndTurnMessage extends RivialProtocol {

    private int id;
    private int gameID;
    private String answer;
    private boolean correct;

    public EndTurnMessage(int id, int gameID, String answer){
        this.id = id;
        this.answer = answer;
        this.gameID = gameID;
    }

    public int getGameID(){ return this.gameID; }

    public int getID(){
        return this.id;
    }

    public String getAnswer(){
        return this.answer;
    }

    public void setCorrect(boolean correct){
        this.correct = correct;
    }

    public boolean isCorrect(){
        return this.correct;
    }

    @Override
    public messageType getMessageType() {
        return messageType.END_TURN;
    }

    @Override
    public RivialHandler getHandler() {
        return new EndTurnHandler(this);
    }
}
