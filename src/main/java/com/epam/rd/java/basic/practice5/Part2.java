package com.epam.rd.java.basic.practice5;

import java.io.InputStream;

public class Part2 {private static final InputStream cachedValuesOfSystemIn = System.in;
    private static final CustomStream MY_OWN_INPUT_STREAM = new CustomStream();

    public static void main(final String[] args) {
        System.setIn(MY_OWN_INPUT_STREAM);

        Thread t = new Thread(() -> Spam.main(null));
        t.start();
        try {
            System.out.println("");
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.setIn(cachedValuesOfSystemIn);

    }

    private static class CustomStream extends InputStream {
        private boolean firstTime = true;

        @Override
        public int read() {

            if (firstTime) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                firstTime = false;

                return "\n".getBytes().length;
            } else {

                return -1;
            }
        }
    }


}
