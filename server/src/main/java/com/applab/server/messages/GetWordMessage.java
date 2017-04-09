package com.applab.server.messages;

import com.applab.server.handlers.GetWordHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class GetWordMessage extends RivialProtocol {

    private int id;
    private String word;
    private String[] alternatives;

    public GetWordMessage(int id){
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setAlternatives(String[] alternatives){
        this.alternatives = alternatives;
    }

    public String getWord(){
        return this.word;
    }

    public String[] getAlternatives(){
        return alternatives;
    }
    @Override
    public messageType getMessageType() {
        return messageType.GET_WORD;
    }

    @Override
    public RivialHandler getHandler() {
        return new GetWordHandler(this);
    }
}
