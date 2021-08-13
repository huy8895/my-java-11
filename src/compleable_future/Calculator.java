package compleable_future;

import java.util.concurrent.TimeUnit;

public class Calculator {
    public static int add(int a, int b) {
        int sum = a + b;
        System.out.println("result: " + Thread.currentThread().getName() + " " + a + " + " + b + " = " + sum);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public int addNomal(int a, int b) {
        int sum = a + b;
        System.out.println("result: " + a + " + " + b + " = " + sum);
        return sum;
    }
}
