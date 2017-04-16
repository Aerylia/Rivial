package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetWordMessage;

import java.io.IOException;


/**
 * Created by arian on 9-4-2017.
 */

public class GetWordHandler extends RivialHandler {

    private GetWordMessage message;

    public GetWordHandler(GetWordMessage message){
        this.message = message;
    }

    @Override
    public void run(){
        try {
            if (serverSide) {
                ReplyProtocol replyProtocol = new ReplyProtocol();
                String word = server.generateNextWord(message.getID());
                String[] words = server.getOtherWords(word);
                message.setWord(word);
                message.setAlternatives(words);
                replyProtocol.addReply(message, clientSocket);
                replyProtocol.sendReplies();
            } else {
                client.handleWord(message.getWord(), message.getAlternatives());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
