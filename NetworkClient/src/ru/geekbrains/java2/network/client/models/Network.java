package ru.geekbrains.java2.network.client.models;

import javafx.application.Platform;
import ru.geekbrains.java2.network.client.NetworkChatClient;
import ru.geekbrains.java2.network.client.controllers.AuthDialogController;
import ru.geekbrains.java2.network.client.controllers.ViewController;
import ru.geekbrains.java2.network.clientserver.Command;
import ru.geekbrains.java2.network.clientserver.CommandType;
import ru.geekbrains.java2.network.clientserver.commands.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.spi.AbstractResourceBundleProvider;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8289;

    private final String host;
    private final int port;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;
    private String username;

    public Network() {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Соединение не было установлено!");
            e.printStackTrace();
            return false;
        }
    }
    public void checkConnectionStatus(){
        Thread thread = new Thread(()-> {
            while(true)
            {
                try {
                    Command command = readCommand();
                    if (command.getType() == CommandType.AUTH_ERROR) {
                        System.out.println("got auth_error command");
                        AuthErrorCommandData data = (AuthErrorCommandData) command.getData();
                        NetworkChatClient.isClose = true;
                        Platform.runLater(() -> {
                            NetworkChatClient.showNetworkError(data.getErrorMessage(), "Connection error");
                        });
                        close();
                        break;
                    }

                } catch (IOException e) {
                    System.err.println("Unknown command");
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    public String sendAuthCommand(String login, String password) {
        try {
            Command authCommand = Command.authCommand(login, password);
            outputStream.writeObject(authCommand);
            Command command = readCommand();
            if(command == null){
                return "Failed to read command from server";
            }
            switch (command.getType()){
                case AUTH_OK: {
                    AuthOkCommandData data = (AuthOkCommandData) command.getData();
                    this.username = data.getUsername();
                    return null;
                }
                case AUTH_ERROR:{
                    System.out.println("got auth_error command");
                    AuthErrorCommandData data = (AuthErrorCommandData) command.getData();
                    return data.getErrorMessage();
                }
                default:
                    return "Unknown type of command from server: " + command.getType();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
    private Command readCommand() throws IOException {
        try {
            return  (Command) inputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            String errorMessage = "Unknown type of object from client!";
            System.err.println(errorMessage);
            e.printStackTrace();
            return null;
        }
    }

    public void waitMessages(ViewController viewController) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Command command = readCommand();
                        if(command == null){
                            viewController.showError("Server error","Invalid command from server!");
                            continue;
                        }
                        switch (command.getType()){
                            case INFO_MESSAGE: {
                                MessageInfoCommandData data = (MessageInfoCommandData) command.getData();
                                String message = data.getMessage();
                                String sender = data.getSender();
                                String formattedMsg = sender != null ? String.format("%s: %s", sender, message) : message;
                                Platform.runLater(() -> {
                                    viewController.appendMessage(formattedMsg);
                                });
                                break;
                            }
                            case UPDATE_USERS_LIST: {
                                UpdateUsersListCommandData data = (UpdateUsersListCommandData) command.getData();
                                Platform.runLater(() -> {
                                    viewController.updateUsers(data.getUsers());
                                });
                                break;
                            }
                            case ERROR: {
                                ErrorCommandData data = (ErrorCommandData) command.getData();
                                String errorMsg = data.getErrorMessage();
                                Platform.runLater(() -> {
                                    viewController.showError("Server error", errorMsg);
                                });
                                break;
                            }
                            default:{
                                viewController.showError("Unknown command from server",command.getType().toString());
                            }

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Соединение было потеряно!");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendPrivateMessage(String message, String recipient) throws IOException {
        Command command = Command.privateMessageCommand(recipient,message);
        sendCommand(command);
    }
    private  void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }
    public void sendMessage(String message) throws IOException {
        Command command = Command.publicMessageCommand(username, message);
        sendCommand(command);
    }
    public void setChatMode() {
        waitMessages(null);
    }

    public String getUsername() {
        return username;
    }


}
