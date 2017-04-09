package com.applab.server;
// How to get the server running seperately: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html

import com.applab.model.GameModel;
import com.applab.server.handlers.RivialHandler;
import com.applab.server.messages.RivialProtocol;

import java.net.*;
import java.io.*;

public class RivialServer {

    int portNumber;
    private GameModel game;
    public Socket currentClient;

    public RivialServer(int portNumber, GameModel game){
        this.portNumber = portNumber;
        this.game = game;
    }

    public int addClient(Socket client){
        //TODO impl.
        return 10;
    }

    public int getGames(){
        // TODO impl.
        return 1;
    }

    public void joinGame(int clientID, int gameID){
        // TODO impl.
    }

    public int createGame(){
        // TODO impl.
        return 2;
    }

    public boolean endTurn(int id, String answer){
        // TODO check if answer is correct.
        return false;
    }

    public String generateNextWord(int id){
        // TODO generate word with slimstampen.
        return "";
    }

    public String[] getOtherWords(String word){
        // TODO generate words for multiple choice unequal to word!
        return new String[0];
    }

    public Socket[] getPlayers(int gameID){
        // TODO get players for a game
        return new Socket[0];
    }

    public boolean startGame(int userID, int gameID){
        // TODO impl Check for allowed start! and start game .
        return false;
    }

    public boolean checkAllEndTurn(int gameID){
        // TODO check if all players past their turn
        // TODO If so, also set back to no end Turn!!
        return false;
    }

    public boolean checkEndGame(int gameID){
        // TODO check if end game and quit game!
        return false;
    }

    private void runServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (true){ // TODO add threading
                currentClient = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(
                        currentClient.getInputStream());
                try {
                    // Read protocol
                    Object input = in.readObject();
                    RivialProtocol protocol = (RivialProtocol) input;
                    // Handle message
                    RivialHandler handler = protocol.getHandler();
                    ReplyProtocol reply = handler.handleServerSide(this);
                    // Send reply
                    reply.sendReplies();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        GameModel game = new GameModel();
        RivialServer server = new RivialServer(5964, game);
        server.runServer();

    }

}
