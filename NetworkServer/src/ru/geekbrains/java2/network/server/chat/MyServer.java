package ru.geekbrains.java2.network.server.chat;

import ru.geekbrains.java2.network.clientserver.Command;
import ru.geekbrains.java2.network.server.ThreadExecutor;
import ru.geekbrains.java2.network.server.chat.auth.AuthService;
import ru.geekbrains.java2.network.server.chat.auth.BaseAuthService;
import ru.geekbrains.java2.network.server.chat.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final AuthService authService;
    private final Database database;
    private final IThreadExecutor threadExecutor;

    public IThreadExecutor getThreadExecutor() {
        return threadExecutor;
    }

    public MyServer(int port) throws IOException {
        this.threadExecutor = new ThreadExecutor();
        this.serverSocket = new ServerSocket(port);
        this.database = new Database();
        this.authService = new BaseAuthService();
    }

    public void start() throws IOException {
        System.out.println("Сервер был запущен");

        authService.start();
        authService.setUsers(database.getUsers());
        try {
            while (true) {
                waitAndProcessNewClientConnection();
            }
        } catch (IOException e) {
            System.err.println("Failed to accept new connection");
            e.printStackTrace();
        } finally {
            authService.stop();
            serverSocket.close();
            database.stop();
            threadExecutor.close();
        }
    }

    private void waitAndProcessNewClientConnection() throws IOException {
        System.out.println("Ожидание нового подключения....");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился");// /auth login password
        processClientConnection(clientSocket);
    }

    private void processClientConnection(Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void broadcastMessage(ClientHandler sender, Command command) throws IOException {
        for (ClientHandler client : clients) {
            if (client == sender) {
                continue;
            }
            client.sendMessage(command);
        }
    }

    public synchronized void sendPrivateMessage(String recipient, Command command) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(recipient)) {
                client.sendMessage(command);
            }
        }
    }

    public synchronized void subscribe(ClientHandler handler) throws IOException {
        clients.add(handler);
        List<String> userNames = getAllUserNames();
        broadcastMessage(null,Command.updateUsersListCommand(userNames));
    }

    private List<String> getAllUserNames() {
        List<String> userNames = new ArrayList<>();
        for (ClientHandler client : clients) {
            userNames.add(client.getUsername());
        }
        return userNames;
    }

    public synchronized void unsubscribe(ClientHandler handler) throws IOException {
        clients.remove(handler);
        List<String> userNames = getAllUserNames();
        broadcastMessage(null,Command.updateUsersListCommand(userNames));
    }

    public synchronized void update() throws IOException {
        List<String> userNames = getAllUserNames();
        broadcastMessage(null,Command.updateUsersListCommand(userNames));
    }

    public synchronized boolean isNicknameAlreadyBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void changeUsername(String username, String newUserName) throws SQLException {
        System.out.format("%s -> %s%n",username,newUserName);
        database.changeUserName(username,newUserName);
    }
}
