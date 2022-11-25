package org.example;

public class Producer extends Thread {
    private final Type type;
    Monitor monitor;


    public Producer(Monitor monitor, Type type) {
        this.monitor = monitor;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.printf("[%s] producer started\n", type);
                monitor.produce(getRandomNumber(type.getValue()), type);
                System.out.printf("[%s] producer ended\n", type);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getRandomNumber(int max) {
        return (int) ((Math.random() * (max - 1)) + 1);
    }

    public Type getType() {
        return type;
    }
}
