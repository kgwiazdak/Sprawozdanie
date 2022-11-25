package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Buffor buffor = new Buffor();
    ReentrantLock producersLock = new ReentrantLock();
    ReentrantLock consumersLock = new ReentrantLock();
    ReentrantLock commonLock = new ReentrantLock();

    Condition producerCondition = commonLock.newCondition();
    Condition consumerCondition = commonLock.newCondition();

    private int bigConsumerCounter = 0;
    private int smallConsumerCounter = 0;
    private int bigProducerCounter = 0;
    private int smallProducerCounter = 0;

    public void consume(int numberToConsume, Type type) throws InterruptedException {
        try {
            consumersLock.lock();
            try {
                commonLock.lock();
                while (buffor.getNumber() < numberToConsume) {
                    consumerCondition.await();
                }
                int currentNumber = buffor.getNumber();
                buffor.setNumber(currentNumber - numberToConsume);

                if (type.equals(Type.BIG)) {
                    bigConsumerCounter++;
                } else {
                    smallConsumerCounter++;
                }
                showStats();
            } finally {
                producerCondition.signal();
                commonLock.unlock();
            }
        } finally {
            consumersLock.unlock();
        }
    }


    public void produce(int numberToProduce, Type type) throws InterruptedException {
        try {
            producersLock.lock();
            try {
                commonLock.lock();
                while (buffor.getNumber() + numberToProduce > buffor.getMaximalNumber()) {
                    producerCondition.await();
                }
                int currentNumber = buffor.getNumber();
                buffor.setNumber(currentNumber + numberToProduce);
                if (type.equals(Type.BIG)) {
                    bigProducerCounter++;
                } else {
                    smallProducerCounter++;
                }
                showStats();
            } finally {
                consumerCondition.signal();
                commonLock.unlock();
            }
        } finally {
            producersLock.unlock();
        }
    }
    private void showStats() {
        System.out.println("smallConsumerCounter: " + smallConsumerCounter);
        System.out.println("bigConsumerCounter: " + bigConsumerCounter);
        System.out.println("smallProducerCounter: " + smallProducerCounter);
        System.out.println("bigProducerCounter: " + bigProducerCounter);
    }
}

