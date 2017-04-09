package com.applab.server.handlers;

import com.applab.server.ReplyProtocol;
import com.applab.server.RivialServer;
import com.applab.server.TempRivialClient;

/**
 * Created by arian on 9-4-2017.
 */

public interface RivialHandler {


    public ReplyProtocol handleServerSide(RivialServer server);
    public ReplyProtocol handleClientSide(TempRivialClient client);
}
