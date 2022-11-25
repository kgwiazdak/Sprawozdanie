package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        ArrayList<Thread> producersAndConsumers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            producersAndConsumers.add(new Consumer(monitor, Type.SMALL));
            producersAndConsumers.add(new Producer(monitor, Type.SMALL));
        }
        producersAndConsumers.add(new Producer(monitor, Type.BIG));
        producersAndConsumers.add(new Consumer(monitor, Type.BIG));

        for (Thread thread : producersAndConsumers)
            thread.start();

        for (Thread thread : producersAndConsumers)
            thread.join();
    }
}