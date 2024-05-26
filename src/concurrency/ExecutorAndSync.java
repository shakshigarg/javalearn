package concurrency;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorAndSync {
    int sum = 0;

    public synchronized void calculate() {
        sum = getSum() + 1;
    }

    public int getSum() {
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ExecutorAndSync executorAndSync = new ExecutorAndSync();
        IntStream.range(0, 1000).forEach((count) -> executorService.submit(executorAndSync::calculate));
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        System.out.println(executorAndSync.getSum());
        executorService.shutdown();

    }
}
