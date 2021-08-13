package compleable_future;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> Calculator.add(1, 2), executor);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> new Calculator().addNomal(1, 3), executor);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> Calculator.add(2, 3), executor);
        final var stringCompletableFuture = CompletableFuture.supplyAsync(() -> "hi", executor);
        System.out.println("Done");

        executor.shutdown();
    }
}

class Demo2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final var atomicInteger = new AtomicInteger();
        final var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final var collect = list.stream()
                                .map(i -> CompletableFuture.supplyAsync(() -> Calculator.add(i, 2), executor))
                                .collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(collect.toArray(new CompletableFuture[0]));

        final var listCompletableFuture = allFutures.thenApply(v -> collect.stream()
                                                                       .map(CompletableFuture::join)
                                                                       .collect(Collectors.toList()));
        listCompletableFuture.thenAccept(integers -> System.out.println("integers = " + integers));
        System.out.println("Done");
        executor.shutdown();
    }
}

class Demo4 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> Calculator.add(1, 2), executor);

        future.thenApplyAsync(data -> {
            System.out.println("CompletableFuture 1 done");
            return Calculator.add(1, 3); // CompletableFuture 2
        })
              .thenApplyAsync(data -> {
                  System.out.println("CompletableFuture 2 done");
                  return Calculator.add(2, 3); // CompletableFuture 3
              })
              .thenAcceptAsync(data -> {
                  System.out.println("CompletableFuture 3 done");
              })
              .thenRun(() -> {
                  System.out.println("Finished!");
              });

        executor.shutdown();
    }
}

class Demo5 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> Calculator.add(1, 2), executor);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> Calculator.add(1, 3), executor);
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> Calculator.add(2, 3), executor);
        final var futureAll = CompletableFuture.allOf(future1, future2, future3);
        futureAll.thenRunAsync(() -> {
            System.out.println("All future is Done!");
        });
        System.out.println("Done");
        executor.shutdown();
    }
}

class Demo6 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> welcomeText =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new IllegalStateException(e);
                    }
                    return "Rajeev";
                })
                                 .thenApply(name -> "Hello " + name)
                                 .thenApply(greeting -> greeting + ", Welcome to the CalliCoder Blog");

        System.out.println(welcomeText.get());
// Prints - Hello Rajeev, Welcome to the CalliCoder Blog
    }
}
