package ru.geekbrains.java2.network.server.chat.handler;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.geekbrains.java2.network.clientserver.Command;
import ru.geekbrains.java2.network.clientserver.CommandType;
import ru.geekbrains.java2.network.clientserver.commands.AuthCommandData;
import ru.geekbrains.java2.network.clientserver.commands.ChangeNameCommandData;
import ru.geekbrains.java2.network.clientserver.commands.PrivateMessageCommandData;
import ru.geekbrains.java2.network.clientserver.commands.PublicMessageCommandData;
import ru.geekbrains.java2.network.server.chat.MyServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {

    private final MyServer myServer;
    private final Socket clientSocket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String username;

    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.myServer = myServer;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        in  = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

        myServer.getThreadExecutor().submit(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    logger.log(Level.ERROR,"Failed to close connection!");
                }
            }
        });
    }

    public String getUsername() {
        return username;
    }

    private void readMessages() throws IOException {
        while (true) {
            Command command = readCommand();
            if(command == null){
                continue;
            }
            switch (command.getType()){
                case END:
                    return;
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String recipient = data.getReceiver();
                    String privateMessage = data.getMessage();
                    myServer.sendPrivateMessage(recipient,Command.messageInfoCommand(privateMessage, username));
                    logger.log(Level.INFO,String.format("client: %s sent a private message",recipient));
                    break;
                }
                case PUBLIC_MESSAGE:{
                    PublicMessageCommandData data  = (PublicMessageCommandData) command.getData();
                    String message = data.getMessage();
                    String sender = data.getSender();
                    myServer.broadcastMessage(this,Command.messageInfoCommand(message,sender));
                    logger.log(Level.INFO,String.format("client: %s sent a broadcast message",sender));
                    break;
                }case CHANGE_NAME:{
                    ChangeNameCommandData data  = (ChangeNameCommandData) command.getData();
                    String username = data.getUsername();
                    String newUserName = data.getNewUserName();
                    logger.log(Level.INFO,String.format("client: %s sent a command to change his name",username));
                    try {
                        myServer.changeUsername(username, newUserName);
                        this.username = newUserName;
                        myServer.sendPrivateMessage(this.username,Command.changeNameCommand(username,newUserName));
                    } catch (SQLException throwables) {
                        logger.log(Level.ERROR,"Can't change username! Database exception! \n"+throwables.getMessage());
                    }
                    myServer.update();
                    myServer.sendPrivateMessage(this.username,Command.messageInfoCommand(username+"(you) changed username to "+ newUserName,"Server"));
                    break;
                }
                default:
                    logger.log(Level.ERROR,"Unknown type of command: "+command.getType());
            }
        }
    }

    private Command readCommand() throws IOException {
        try {
            return  (Command) in.readObject();
        } catch (ClassNotFoundException | SocketException e) {
            String errorMessage = "Unknown type of object from client!";
            logger.log(Level.ERROR,errorMessage);
            e.printStackTrace();
            sendMessage(Command.errorCommand(errorMessage));
            return null;
        }
    }

    private void authentication() throws IOException {
        myServer.getThreadExecutor().submit(this::closeConnectionTask);
        while (true) {
            Command command = readCommand();
            if(command == null){
                continue;
            }
            if(command.getType() == CommandType.AUTH){
                boolean isSuccessAuth = processAuthCommand(command);
                logger.log(Level.INFO,"A client sent authentication command");
                if(isSuccessAuth) break;

            }
            else {
                sendMessage(Command.authErrorCommand("Auth command is required!"));
            }
        }
    }

    private void closeConnectionTask() {
        TimerTask closeConnectionTask = new TimerTask() {
            @Override
            public void run() {
                if(username == null) {
                    try {
                        Command command = Command.authErrorCommand("authentication time out: > 120sec");
                        out.writeObject(command);
                        logger.log(Level.INFO,"closing connection due to timeout...");
                        Thread.sleep(500);
                        closeConnection();
                    } catch (IOException | InterruptedException e) {
                        logger.log(Level.ERROR,"Client socket error "+ e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
        new Timer().schedule(closeConnectionTask,120_000);
    }

    private boolean processAuthCommand(Command command) throws IOException {
        AuthCommandData authCommandData = (AuthCommandData) command.getData();
        String login = authCommandData.getLogin();
        String password = authCommandData.getPassword();
        this.username = myServer.getAuthService().getUsernameByLoginAndPassword(login, password);
        if (username != null) {
            if (myServer.isNicknameAlreadyBusy(username)) {
                sendMessage(Command.authErrorCommand("Login and password are already used!"));
                return false;
            }
            sendMessage(Command.authOkCommand(username));
            String message = username + " joined the chat!";
            logger.log(Level.INFO,message);
            myServer.broadcastMessage(this, Command.messageInfoCommand(message,null));
            myServer.subscribe(this);
            return true;
        } else {
            sendMessage(Command.authErrorCommand("Login and/or password are invalid! Please, try again"));
            return false;
        }
    }

    private void closeConnection() throws IOException {
        myServer.unsubscribe(this);
        clientSocket.close();
    }


    public void sendMessage(Command command) throws IOException {
        out.writeObject(command);
    }
}
