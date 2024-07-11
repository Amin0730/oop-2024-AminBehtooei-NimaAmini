package org.example;

import java.util.Scanner;

public class CountdownTimer {
    private Scanner scanner;
    public CountdownTimer(Scanner scanner) {
        this.scanner = scanner;
    }
    public void startCountdown(int delay) {
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < delay) {
            int remainingTime = (int) Math.ceil((delay - (System.currentTimeMillis() - startTime)) / 1000.0);
            System.out.println("Time remaining: " + remainingTime + " seconds");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
                System.out.println("Please wait until the timer finishes.");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
