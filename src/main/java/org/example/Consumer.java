package org.example;

public class Consumer extends Thread {
    Monitor monitor;
    private final Type type;


    public Consumer(Monitor monitor, Type type) {
        this.monitor = monitor;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.printf("[%s] consumer started\n", type);
                monitor.consume(getRandomNumber(type.getValue()), type);
                System.out.printf("[%s] consumer ended\n", type);
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

