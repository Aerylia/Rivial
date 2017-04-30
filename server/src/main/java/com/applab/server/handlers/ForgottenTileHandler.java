package com.applab.server.handlers;

import com.applab.exceptions.GameNotFoundException;
import com.applab.exceptions.PlayerNotFoundException;
import com.applab.exceptions.TileNotFoundException;
import com.applab.model.Player;
import com.applab.server.ReplyProtocol;
import com.applab.server.messages.ForgottenTileMessage;

import java.io.IOException;

/**
 * Created by arian on 18-4-2017.
 */

public class ForgottenTileHandler extends RivialHandler {

    private ForgottenTileMessage message;

    public ForgottenTileHandler(ForgottenTileMessage message){
        this.message = message;
    }

    @Override
    public void run() {
        if(serverSide){
            try {
                server.handleForgottenTile(message.getGame(), message.getPlayer(), message.getTile());
                ReplyProtocol replyProtocol = new ReplyProtocol();
                for (Player player : server.getPlayers(message.getGame())) {
                    replyProtocol.addReply(message, player.getSocket());
                }
                replyProtocol.sendReplies();
            } catch (PlayerNotFoundException e){
                e.printStackTrace();
            } catch (GameNotFoundException e){
                e.printStackTrace();
            } catch (TileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                client.handleForgottenTile(message.getGame(), message.getPlayer(), message.getTile());
            } catch (PlayerNotFoundException e){
                e.printStackTrace();
            } catch (TileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
