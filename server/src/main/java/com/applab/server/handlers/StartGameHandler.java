package com.applab.server.handlers;

import com.applab.exceptions.GameNotFoundException;
import com.applab.exceptions.PlayerNotFoundException;
import com.applab.model.Player;
import com.applab.server.ReplyProtocol;
import com.applab.server.messages.StartGameMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by arian on 9-4-2017.
 */

public class StartGameHandler extends RivialHandler {

    StartGameMessage message;

    public StartGameHandler(StartGameMessage message){
        this.message = message;
    }

    @Override
    public void run() {
        try {
            if (serverSide) {
                try {
                    ReplyProtocol replyProtocol = new ReplyProtocol();
                    ArrayList<Player> players = server.getPlayers(message.getGame());
                    if (server.canStartGame(message.getPlayer(), message.getGame())) {
                        message.setStarted(true);
                        for (Player player : players) {
                            replyProtocol.addReply(message, player.getSocket());
                        }
                    } else {
                        message.setStarted(false);
                        for(Player player: players){
                            if(player.getId() == message.getPlayer()){
                                replyProtocol.addReply(message, player.getSocket());
                            }
                        }
                    }
                    replyProtocol.sendReplies();
                } catch (PlayerNotFoundException e){
                    e.printStackTrace();
                } catch (GameNotFoundException e){
                    e.printStackTrace();
                }
            } else {
                if(message.isStarted()) {
                    client.startGame();
                } else {
                    client.startFailed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
