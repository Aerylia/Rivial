package com.applab.server;
// How to get the server running seperately: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html

import com.applab.exceptions.GameNotFoundException;
import com.applab.model.GameModel;
import com.applab.model.Player;
import com.applab.model.WordList;
import com.applab.server.handlers.RivialHandler;
import com.applab.server.messages.RivialProtocol;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class RivialServer implements Runnable{

    int portNumber;
    private ArrayList<GameModel> games;
    private ServerSocket serverSocket;
    private ArrayList<Player> clients;
    private WordList words;

    public RivialServer(int portNumber, String filename) throws IOException{
        this.games = new ArrayList<>();
        this.serverSocket = new ServerSocket(portNumber);
        this.clients = new ArrayList<>();
        this.portNumber = portNumber;
        this.words = WordList.loadFromFile(filename);
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public Player addClient(Socket client){
        for(Player player: clients){
            if(player.getSocket().equals(client)){
                return player;
            }
        }
        Player player = new Player(client, clients.size());
        clients.add(player);
        return player;
    }

    public ArrayList<GameModel> getGames(){
        return games;
    }

    public boolean joinGame(Player player, GameModel game){
        return game.addPlayer(player);
    }

    public GameModel createGame(Player player){
        GameModel game = new GameModel(this.words, this.games.size(), player);
        this.games.add(game);
        return game;
    }

    private GameModel getGameWithID(int gameID) throws GameNotFoundException{
        for(GameModel game : games){
            if(game.getId() == gameID){
                return game;
            }
        }
        throw new GameNotFoundException();
    }

    public ArrayList<Player> getPlayers(GameModel game) {
        return game.getPlayers();
    }

    public boolean canStartGame(Player player, GameModel game) {
        return game.canStartGame(player);
    }

    @Override
    public void run() {
        try {
            while (true){
                for(Player player: clients) {
                    Socket client = player.getSocket();
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
        String filename = "TODO.csv";
        int port = 5964;
        try {
            RivialServer server = new RivialServer(port, filename );
            RivialDaemon daemon = new RivialDaemon(server);
            daemon.start();
            (new Thread(server)).start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
