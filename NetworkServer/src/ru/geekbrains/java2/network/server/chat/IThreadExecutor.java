package ru.geekbrains.java2.network.server.chat;

public interface IThreadExecutor {
    void init();

    void submit(Runnable task);

    void close();
}
