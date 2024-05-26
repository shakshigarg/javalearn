package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsThreadpoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("running");
            }
        };
        for(int i=0;i<10;i++){
            executorService.execute(runnable);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){

        }
        System.out.println("finishing all");

    }
}
