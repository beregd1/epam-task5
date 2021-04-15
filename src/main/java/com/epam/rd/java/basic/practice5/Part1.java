package com.epam.rd.java.basic.practice5;

public class Part1 {
    public static void main(String[] args) {

        Thread t1 = new MyThread();
        t1.setName("firstChild");
        t1.start();
        Thread t2 = new Thread(new MyThread2("secondChild"));
        try {
            t1.join();
            t2.start();
            t2.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(this.getName());
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyThread2 implements Runnable {
        String name;

        public MyThread2(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4; i++) {
                System.out.println(this.name);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

}
