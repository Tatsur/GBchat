package ru.geekbrains.java2.network.server.chat.auth;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.geekbrains.java2.network.server.chat.User;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private static final Logger logger = Logger.getLogger(BaseAuthService.class.getName());
    private static final List<User> USERS = new ArrayList<>();

    @Override
    public void start() {
        logger.log(Level.INFO,"Auth service has been started");
    }

    @Override
    public void stop() {
        logger.log(Level.INFO,"Auth service has been finished");
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User user : USERS) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user.getUsername();
            }
        }

        return null;
    }
    @Override
    public void setUsers(List<User> users) {
        USERS.addAll(users);
    }
}
