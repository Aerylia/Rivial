package com.applab.model;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by arian on 18-4-2017.
 */

public class Player implements Serializable{

    private Socket socket;
    private int id;
    private int color;
    private String name;
    public static final int BLUE = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;

    public Player(Socket socket, int id, int color, String name){
        this.socket = socket;
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getName(){
        return name;
    }

}
