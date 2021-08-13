package compleable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

class MathUtil {
    public static int times(int number, int times) {
        return number * times;
    }

    public static int squared(int number) {
        return number * number;
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}

public class CompletableFuture5 {

    public static final int NUMBER = 5;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Create a CompletableFuture
        final Supplier<Integer> integerSupplier = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return MathUtil.times(NUMBER, 2);
        };

        // Attach a callback to the Future using thenApply()
        final Function<Integer, Integer> integerIntegerFunction = n -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return MathUtil.squared(n);
        };

        final Function<Integer, Boolean> integerBooleanFunction = n -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return MathUtil.isEven(n);
        };
        final var booleanCompletableFuture = CompletableFuture.supplyAsync(integerSupplier)
                                                              .thenApply(integerIntegerFunction)
                                                              .thenApply(integerBooleanFunction);

        // Block and get the result of the future.
        System.out.println(booleanCompletableFuture.get()); // true
    }
}