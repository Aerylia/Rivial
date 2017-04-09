package com.applab.server.messages;

import com.applab.server.handlers.GetGamesHandler;
import com.applab.server.handlers.RivialHandler;

/**
 * Created by arian on 9-4-2017.
 */

public class GetGamesMessage extends RivialProtocol {


    private int games; //TODO make proper class.

    public void addGames(int games){
        this.games = games;
    }

    public int getGames() {
        return games;
    }

    @Override
    public messageType getMessageType() {
        return messageType.GET_GAMES;
    }

    @Override
    public RivialHandler getHandler() {
        return new GetGamesHandler(this);
    }
}
