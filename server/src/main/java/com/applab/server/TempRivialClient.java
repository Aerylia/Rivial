package com.applab.server;

/**
 * Created by arian on 9-4-2017.
 */

import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.server.handlers.RivialHandler;
import com.applab.server.messages.CreateGameMessage;
import com.applab.server.messages.GetGamesMessage;
import com.applab.server.messages.InitMessage;
import com.applab.server.messages.JoinGameMessage;
import com.applab.server.messages.RivialProtocol;
import com.applab.server.messages.StartGameMessage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TempRivialClient implements Runnable {

    private int portNumber;
    private String ip;
    private GameModel game; //TODO make this client game model, different from server model!!! possibly
    private Socket socket;
    private Player player;

    public TempRivialClient(String ip, int port) throws IOException{
        this.portNumber = port;
        this.ip = ip;
        this.socket = new Socket(this.ip, this.portNumber);
        this.initializeConnection();
    }

    public Socket getSocket(){
        return this.socket;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){ return player; }

    public void handleGames(ArrayList<GameModel> games){
        //TODO impl. Show games and let the user pick one.
    }

    public void handleGame(GameModel game){
        this.game = game;
    }

    public void endGame(){
        // TODO impl.
    }

    public void startGame(){
        // TODO impl.
    }

    public void playerJoinedGame(Player player, GameModel game){
        if (this.game == null){
            this.game = game;
            this.game.addPlayer(player);
        } else if(this.game.equals(game)) { // TODO check & send Exception!
            this.game.addPlayer(player); // TODO check & send exception!
        }
    }

    public void initializeStartGame(){
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new StartGameMessage(game, player), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initializeConnection(){
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new InitMessage(), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getGames(){
        try {
            System.out.println("Game: Requesting list of Games!");
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new GetGamesMessage(), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void joinGame(GameModel game){
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new JoinGameMessage(player, game), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createGame(){
        System.out.println("Game: Creating new Game");
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new CreateGameMessage(this.player), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startFailed() {
        // TODO
    }

    public void run(){
        while(true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                try {
                    // Read protocol
                    Object input = in.readObject();
                    RivialProtocol protocol = (RivialProtocol) input;
                    // Handle message
                    RivialHandler handler = protocol.getHandler();
                    handler.handleClientSide(this);
                    System.out.println("Started!");
                    Thread.yield();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        try {
            TempRivialClient temp = new TempRivialClient("localhost", 5964);
            (new Thread(temp)).start();
            temp.createGame();
            temp.getGames();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
