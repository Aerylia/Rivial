package com.applab.server;
// How to get the server running seperately: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html

import com.applab.exceptions.GameNotFoundException;
import com.applab.exceptions.PlayerNotFoundException;
import com.applab.exceptions.TileNotFoundException;
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
        Thread thread = new Thread(new ReadThread(this, client));
        thread.start();
        return player;
    }

    public ArrayList<GameModel> getGames(){
        return games;
    }

    public boolean joinGame(int player, int game) throws GameNotFoundException, PlayerNotFoundException{
        return this.getGameWithID(game).addPlayer(getPlayerWithId(player));
    }

    private Player getPlayerWithId(int id) throws PlayerNotFoundException{
        for(Player player: this.clients){
            if(player.getId() == id){
                return player;
            }
        }
        throw new PlayerNotFoundException();
    }

    public int createGame(int player) throws PlayerNotFoundException{
        GameModel game = new GameModel(this.words, this.games.size(), this.getPlayerWithId(player));
        this.games.add(game);
        return game.getId();
    }

    public boolean isEndGame(int game) throws GameNotFoundException{
        return getGameWithID(game).isEndGame();
    }

    private GameModel getGameWithID(int gameID) throws GameNotFoundException{
        for(GameModel game : games){
            if(game.getId() == gameID){
                return game;
            }
        }
        throw new GameNotFoundException();
    }

    public ArrayList<Player> getPlayers(int game) throws GameNotFoundException{
        return this.getGameWithID(game).getPlayers();
    }

    public boolean canStartGame(int playerID, int gameID) throws GameNotFoundException, PlayerNotFoundException{
        GameModel game = getGameWithID(gameID);
        Player player = getPlayerWithId(playerID);
        return game.canStartGame(player);
    }

    public void handleCapturedTile(int game, int player, int tile) throws TileNotFoundException, GameNotFoundException, PlayerNotFoundException{
        this.getGameWithID(game).tileCaptured(tile, player);
    }

    public void handleForgottenTile(int game, int player, int tile) throws TileNotFoundException, GameNotFoundException, PlayerNotFoundException{
        this.getGameWithID(game).tileForgotten(tile, player);
    }

    @Override
    public void run() {
        while(true){
            try {
                Socket currentClient = this.getServerSocket().accept();
                System.out.println("Server: NEW CONNECTION " + currentClient.toString());
                ObjectInputStream in = new ObjectInputStream(
                        currentClient.getInputStream());
                try {
                    // Read protocol
                    Object input = in.readObject();
                    RivialProtocol protocol = (RivialProtocol) input;
                    // Handle message
                    RivialHandler handler = protocol.getHandler();
                    handler.handleServerSide(this, currentClient);
                    this.addClient(currentClient);
                    Thread.yield();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e){
                System.out.println("Exception caught when trying to listen for a connection");
                System.out.println(e.getMessage());
            }
        }

    }

    public static void main(String[] args) throws IOException {
        String filename = "TODO.csv"; // Also change at clientside
        int port = 5964;
        try {
            RivialServer server = new RivialServer(port, filename );
            (new Thread(server)).start();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private class ReadThread implements Runnable {

        private RivialServer server;
        private Socket client;
        private boolean connected;

        public ReadThread(RivialServer server, Socket client){
            this.server = server;
            this.client = client;
            connected = true;
        }

        public void disconnect(){
            this.connected = false;
        }

        public Thread reconnect(){
            this.connected = true;
            Thread thread = new Thread(this);
            thread.start();
            return thread;
        }

        @Override
        public void run() {
            while(connected) {
                try {
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                    Object input = in.readObject();
                    RivialProtocol protocol = (RivialProtocol) input;
                    // Handle message
                    RivialHandler handler = protocol.getHandler();
                    handler.handleServerSide(server, client);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
