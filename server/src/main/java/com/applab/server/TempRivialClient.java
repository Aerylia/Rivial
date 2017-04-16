package com.applab.server;

/**
 * Created by arian on 9-4-2017.
 */

import com.applab.model.GameModel;
import com.applab.server.handlers.RivialHandler;
import com.applab.server.messages.CreateGameMessage;
import com.applab.server.messages.EndTurnMessage;
import com.applab.server.messages.GetGamesMessage;
import com.applab.server.messages.InitMessage;
import com.applab.server.messages.JoinGameMessage;
import com.applab.server.messages.RivialProtocol;
import com.applab.server.messages.StartGameMessage;

import java.io.*;
import java.net.*;

public class TempRivialClient implements Runnable {

    private int portNumber;
    private String ip;
    private GameModel game; //TODO make this client game model, different from server model!!! possibly
    private int id;
    private Socket socket;

    public Socket getSocket(){
        return this.socket;
    }

    public TempRivialClient(GameModel game, String ip, int port) throws IOException{
        this.portNumber = port;
        this.ip = ip;
        this.socket = new Socket(this.ip, this.portNumber);
    }

    public void setID(int id){
        this.id = id;
    }
    public int getID(){ return id; }

    public void handleGames(int games){
        //TODO impl.
    }

    public void handleGame(int game){
        // TODO impl.
    }

    public void endGame(){
        // TODO impl.
    }

    public void answerCorrect(boolean isCorrect){
        // TODO impl.
    }

    public void handleWord(String word, String[] alternatives){
        // TODO impl.
    }

    public void nextTurn(){
        // TODO impl.
    }

    public void startGame(){
        // TODO impl.
    }

    public void playerJoinedGame(int userID){
        // TODO impl.
    }

    public void endTurn(int gameID, String answer){
        System.out.println("Game: Ending turn!");
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new EndTurnMessage(this.id, gameID, answer), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initStartGame(int gameID){
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new StartGameMessage(this.id, gameID), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initializeConnection(){
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

    public void joinGame(int gameID){
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new JoinGameMessage(this.id, gameID), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createGame(){
        System.out.println("Game: Creating new Game");
        try {
            ReplyProtocol reply = new ReplyProtocol();
            reply.addReply(new CreateGameMessage(this.id), socket);
            reply.sendReplies();
        } catch (IOException e){
            e.printStackTrace();
        }
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
            GameModel game = new GameModel();
            TempRivialClient temp = new TempRivialClient(game, "localhost", 5964);
            temp.initializeConnection();
            (new Thread(temp)).start();
            temp.getGames();
            temp.createGame();
            temp.endTurn(56, "Hello World!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
