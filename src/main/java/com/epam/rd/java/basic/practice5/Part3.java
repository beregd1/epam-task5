package com.epam.rd.java.basic.practice5;

public class Part3 {private int counter;
    private int counter2;
    private Thread[] threads;

    private final int numberOfThreads;
    private final int numberOfIterations;

    public Part3 (int numberOfThreads, int numberOfIterations) {
        this.threads = new Thread[numberOfThreads];
        this.numberOfThreads = numberOfThreads;
        this.numberOfIterations = numberOfIterations;
    }

    public static void main(final String[] args) {

        Part3 element1 = new Part3(3, 2);
        element1.compare();
        element1.compareSync();

    }

    public void compare() {
        clearCounters();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t2 = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    threadJob();
                }
            });
            threads[i] = t2;
            t2.start();

        }
        joinThread();

    }

    public void compareSync() {
        clearCounters();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t2 = new Thread(() -> {
                for (int j= 0; j < numberOfIterations; j++) {
                    synchronized (Part3.class) {
                        threadJob();
                    }
                }
            });
            threads[i] = t2;
            t2.start();

        }
        joinThread();
    }

    private void threadJob() {
        System.out.println(counter + " " + counter2);
        counter++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        counter2++;
    }

    private void joinThread() {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }


    private void clearCounters() {
        counter = 0;
        counter2 = 0;
    }
}
