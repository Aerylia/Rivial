package com.applab.server.messages;

import com.applab.model.GameModel;
import com.applab.server.handlers.GetGamesHandler;
import com.applab.server.handlers.RivialHandler;

import java.util.ArrayList;

/**
 * Created by arian on 9-4-2017.
 */

public class GetGamesMessage extends RivialProtocol {


    private ArrayList<GameModel> games; //TODO make proper class.

    public void addGames(ArrayList<GameModel> games){
        this.games = games;
    }

    public ArrayList<GameModel> getGames() {
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
