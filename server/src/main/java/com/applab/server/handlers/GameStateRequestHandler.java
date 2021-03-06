package com.applab.server.handlers;

import com.applab.exceptions.GameNotFoundException;
import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.ReplyProtocol;
import com.applab.server.messages.GameStateRequestMessage;

import java.io.IOException;

/**
 * Created by arian on 30-4-2017.
 */

public class GameStateRequestHandler extends RivialHandler {

    private GameStateRequestMessage message;

    public GameStateRequestHandler(GameStateRequestMessage message){
        this.message = message;
    }

    @Override
    public void run() {
        try {
            if(serverSide){
                for(GameModel game: server.getGames()){
                    if(game.getId() == message.getGameID()){
                        message.setGameModel(game);
                    }
                }
                ReplyProtocol replyProtocol = new ReplyProtocol();
                for(Player player: server.getPlayers(message.getGameID())){
                    if(player.getId() == message.getPlayerID()){
                        replyProtocol.addReply(message, player.getSocket());
                    }
                }
                replyProtocol.sendReplies();
            } else {
                client.handleGameState(message.getGameModel());
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (GameNotFoundException e){
            e.printStackTrace();
        }
    }
}
