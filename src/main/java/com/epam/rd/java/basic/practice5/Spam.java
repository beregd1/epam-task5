package com.epam.rd.java.basic.practice5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spam {

    private final Thread[] threads;

    private final String[] messages;
    private final int[] delays;


    public Spam(final String[] messages, final int[] delays) {

        threads = new Thread[messages.length];
        this.messages = messages;
        this.delays = delays;
    }


    public static void main(final String[] args) {

        Spam spam = new Spam(new String[]{"@@@", "bbbbbbb"}, new int[]{333, 222});
        spam.start();
        String str;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            str = reader.readLine();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        spam.stop();


    }

    public void start() {
        for (int i = 0; i < messages.length; i++) {
            Worker t = new Worker();
            t.message = messages[i];
            t.timeToSleep = delays[i];
            t.start();
            threads[i] = t;
        }
    }

    public void stop() {


        for (Thread thr : threads){
            thr.interrupt();
        }
        try {
            for (Thread t: threads) {
                t.join();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static class Worker extends Thread {
        String message;
        int timeToSleep;

        @Override
        public void run() {
            do {
                System.out.println(message);
                try {
                    sleep(timeToSleep);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            } while (!Thread.interrupted());
        }
    }
}