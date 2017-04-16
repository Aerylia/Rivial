package com.applab.server;

import com.applab.server.handlers.RivialHandler;
import com.applab.server.messages.RivialProtocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by arian on 16-4-2017.
 */

public class RivialDaemon implements Runnable{

    private RivialServer server;

    public RivialDaemon(RivialServer server){
        this.server = server;
    }

    public void start(){
        (new Thread(this)).start();
    }

    @Override
    public void run(){
        while(true){
            try {
                    Socket currentClient = server.getServerSocket().accept();
                    System.out.println(currentClient.toString());
                    ObjectInputStream in = new ObjectInputStream(
                            currentClient.getInputStream());
                    try {
                        // Read protocol
                        Object input = in.readObject();
                        RivialProtocol protocol = (RivialProtocol) input;
                        // Handle message
                        RivialHandler handler = protocol.getHandler();
                        handler.handleServerSide(server, currentClient);
                        server.addClient(currentClient);
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
}
