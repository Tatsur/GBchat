package ru.geekbrains.java2.network.server.chat.auth;

import ru.geekbrains.java2.network.server.chat.User;

import java.util.List;

public interface AuthService {

    void start();

    String getUsernameByLoginAndPassword(String login, String password);

    void stop();

    void setUsers(List<User> users);
}
