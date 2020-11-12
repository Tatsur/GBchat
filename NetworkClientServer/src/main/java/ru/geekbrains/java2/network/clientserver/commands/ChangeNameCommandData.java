package ru.geekbrains.java2.network.clientserver.commands;

import java.io.Serializable;

public class ChangeNameCommandData implements Serializable {
    private final String username;
    private final String newUserName;

    public ChangeNameCommandData(String username, String newUserName) {
        this.username = username;
        this.newUserName = newUserName;
    }

    public String getUsername() {
        return username;
    }

    public String getNewUserName() {
        return newUserName;
    }
}
