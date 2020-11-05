package ru.geekbrains.java3.homework.lesson4;

//1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
// Используйте wait/notify/notifyAll.
public class Task1 {

    private static final int REPEAT_AMOUNT = 5;

    private static char currentChar = 'A';

    public static void main(String[] args) {

        Object lock = new Object();

        class Task implements Runnable {

            private final char taskChar;
            private final char nextChar;

            public Task(char taskChar, char nextChar) {
                this.taskChar = taskChar;
                this.nextChar = nextChar;
            }

            @Override
            public void run() {
                for (int i = 0; i < REPEAT_AMOUNT; i++) {
                    synchronized (lock) {
                        try {
                            while (currentChar != taskChar) {
                                lock.wait();
                            }
                                System.out.println(currentChar);
                                currentChar = nextChar;
                                lock.notifyAll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        new Thread(new Task('A', 'B')).start();
        new Thread(new Task('B', 'C')).start();
        new Thread(new Task('C', 'A')).start();
    }
}
