package com.applab.server;
// How to get the server running seperately: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html

import com.applab.model.GameModel;
import com.applab.server.handlers.RivialHandler;
import com.applab.server.messages.RivialProtocol;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class RivialServer implements Runnable{

    int portNumber;
    private GameModel game;
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;

    public RivialServer(int portNumber, GameModel game) throws IOException{
        this.game = game;
        this.serverSocket = new ServerSocket(portNumber);
        this.clients = new ArrayList<>();
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public int addClient(Socket client){
        if(!clients.contains(client)){
            clients.add(client);
        }
        return clients.indexOf(client);
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

    public ArrayList<Socket> getPlayers(int gameID){
        // TODO get players for a game
        return clients;
    }

    public boolean startGame(int userID, int gameID){
        // TODO impl Check for allowed start! and start game .
        return true;
    }

    public boolean checkAllEndTurn(int gameID){
        // TODO check if all players past their turn
        // TODO If so, also set back to no end Turn!!
        return true;
    }

    public boolean checkEndGame(int gameID){
        // TODO check if end game and quit game!
        return false;
    }

    @Override
    public void run() {
        try {
            while (true){
                for(Socket client: clients) {
                    ObjectInputStream in = new ObjectInputStream(
                            client.getInputStream());
                    try {
                        // Read protocol
                        Object input = in.readObject();
                        RivialProtocol protocol = (RivialProtocol) input;
                        // Handle message
                        RivialHandler handler = protocol.getHandler();
                        handler.handleServerSide(this, client);
                        Thread.yield();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
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
        try {
            RivialServer server = new RivialServer(5964, game);
            RivialDaemon deamon = new RivialDaemon(server);
            deamon.start();
            (new Thread(server)).start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
