package ru.geekbrains.java2.network.server;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.geekbrains.java2.network.server.chat.MyServer;

import java.io.IOException;

public class ServerApp {

    private static final int DEFAULT_PORT = 8289;
    private static final Logger logger = Logger.getLogger(ServerApp.class.getName());

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        try {
            new MyServer(port).start();
        } catch (IOException e) {
            logger.log(Level.ERROR,"Failed to create MyServer");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
