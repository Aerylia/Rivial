package com.applab.model;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by arian on 18-4-2017.
 */

public class Player implements Serializable{

    private transient Socket socket;
    private int id;
    private int color;
    public static final int BLUE = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;

    public Player(Socket socket, int id){
        this.socket = socket;
        this.id = id;
    }

    public void setColor(int color){
        this.color = color;
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

}
