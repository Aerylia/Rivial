package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;
import com.applab.server.messages.GetWordMessage;


/**
 * Created by arian on 9-4-2017.
 */

public class GetWordHandler implements RivialHandler {

    private GetWordMessage message;

    public GetWordHandler(GetWordMessage message){
        this.message = message;
    }

    @Override
    public ReplyProtocol handleServerSide(RivialServer server) {
        ReplyProtocol replyProtocol = new ReplyProtocol();
        String word = server.generateNextWord(message.getID());
        String[] words = server.getOtherWords(word);
        message.setWord(word);
        message.setAlternatives(words);
        replyProtocol.addReply(message, server.currentClient);
        return replyProtocol;
    }

    @Override
    public ReplyProtocol handleClientSide(TempRivialClient client) {
        client.handleWord(message.getWord(), message.getAlternatives());
        return new ReplyProtocol();
    }
}
