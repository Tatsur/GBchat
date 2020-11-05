package ru.geekbrains.java2.network.server;

import ru.geekbrains.java2.network.server.chat.IThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor implements IThreadExecutor {

    private int threadsAmount = 20;

    private ExecutorService service;

    public ThreadExecutor() {
        init();
    }

    public ThreadExecutor(int threadsAmount) {
        this.threadsAmount = threadsAmount;
        init();
    }

    @Override
    public void init() {
        service = Executors.newFixedThreadPool(threadsAmount);
    }


    @Override
    public void submit(Runnable task) {
        service.submit(task);
    }

    @Override
    public void close() {
        service.shutdown();
    }
}
