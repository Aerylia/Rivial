package com.applab.server.messages;

import com.applab.model.Player;
import com.applab.server.handlers.InitHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class InitMessage extends RivialProtocol {

    private Player player;

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }

    @Override
    public messageType getMessageType() {
        return messageType.INIT;
    }

    @Override
    public RivialHandler getHandler() {
        return new InitHandler(this);
    }

}
