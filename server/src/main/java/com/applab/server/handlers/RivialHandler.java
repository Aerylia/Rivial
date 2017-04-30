package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;

import java.net.Socket;

/**
 * Created by arian on 9-4-2017.
 */

public abstract class RivialHandler implements Runnable{

    RivialServer server;
    TempRivialClient client;
    Socket clientSocket;
    boolean serverSide;

    public void handleServerSide(RivialServer server, Socket client){
        this.serverSide = true;
        this.server = server;
        this.clientSocket = client;
        System.out.print("Handling serverSide: ");
        System.out.print(this.toString());
        System.out.print("\n");
        (new Thread(this)).start();
    }
    public void handleClientSide(TempRivialClient client) {
        this.serverSide = false;
        this.client = client;
        System.out.print("Handling clientside: ");
        System.out.print(this.toString());
        System.out.print("\n");
        (new Thread(this)).start();
    }



}
