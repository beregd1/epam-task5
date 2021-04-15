package com.epam.rd.java.basic.practice5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part4 {
    static final Pattern pWord = Pattern.compile("\\d+");
    static int[][] matrix;
    static Thread[] threads;
    static int rowsCountM = 0;
    static int max = 0;
    static int max2 = 0;

    public static void main(final String[] args) {
        matrix = getMatrix();
        threads = new Thread[rowsCountM];
        createThreads();

        long startTime = new Date().getTime();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }


        long stopTime = new Date().getTime();
        long time = stopTime - startTime;

        int num = 0;
        long startTime2 = new Date().getTime();
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                num = Math.max(num, anInt);
            }
        }
        max2 = num;
        long stopTime2 = new Date().getTime();
        long time2 = stopTime2 - startTime2;

        System.out.println(max);
        System.out.println(time);
        System.out.println(max2);
        System.out.println(time2);

    }

    private static void createThreads() {

        for (int i = 0; i < rowsCountM; i++) {
            Worker t = new Worker();
            t.numbers = matrix[i];
            threads[i] = t;
        }
    }

    public static int[][] getMatrix() {
        String str;
        int[] size = getMatrixSize();
        int rowsCount = size[0];
        int columnCount = size[1];
        int[][] inputMatrix = new int[rowsCount][columnCount];

        try (BufferedReader reader = new BufferedReader(new FileReader("part4.txt"))) {
            int rowsNumber = -1;
            while ((str = reader.readLine()) != null) {
                if (!str.isEmpty()) {
                    int j = -1;
                    rowsNumber++;
                    Matcher mNumber = pWord.matcher(str);
                    while (mNumber.find()) {
                        j++;
                        inputMatrix[rowsNumber][j] = Integer.parseInt(mNumber.group());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputMatrix;
    }

    private static int[] getMatrixSize() {
        int[] size = new int[2];
        int columnCount = 0;
        int rowCount = 0;
        String str;
        try (BufferedReader reader = new BufferedReader(new FileReader("part4.txt"))) {
            boolean isFirstLine = true;
            while ((str = reader.readLine()) != null) {
                if (!str.isEmpty()) {
                    if (isFirstLine) {
                        Matcher mNumber = pWord.matcher(str);
                        while (mNumber.find()) {
                            columnCount++;
                        }
                        isFirstLine = false;
                    }
                    rowCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        size[0] = rowCount;
        size[1] = columnCount;
        rowsCountM = rowCount;

        return size;
    }

    private static class Worker extends Thread {

        int[] numbers;

        @Override
        public void run() {
            int maxi = 0;
            for (int i : numbers
            ) {
                if (maxi < i) {
                    maxi = i;
                }
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (maxi > max) {
                max = maxi;
            }
        }
    }
}