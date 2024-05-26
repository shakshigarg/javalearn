package concurrency;

import javax.rmi.CORBA.Util;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Utils {
    public static void getFuture(List<Future<?>> list) {
        list.stream().forEach((f) -> {

            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public static void getFuture(List<Future<String>> list, boolean b) {
        list.stream().forEach((f) -> {

            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        });
    }
}

public class ExecuteExecutorsExecutorService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Executor executor = Executors.newSingleThreadExecutor();
//
//        // Execute method of executor only talks runnable ==> cant return anything
//        IntStream.range(0, 10).forEach((count) -> executor.execute(() -> {
//            System.out.println("hello world ");
//        }));


        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // executor service provides submit method, which can take callable and returns future of the value
        Future<String> future = executorService.submit(() -> {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Thread.currentThread().getName();
        });
        System.out.println(future.get());

        // executor service can also take runnable and a value to return as a result

        List<Future<?>> futureRunnable = IntStream.range(1, 10).mapToObj((count) -> executorService.submit(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hello " + count);
                    }
                }, count)).collect(Collectors.toList());

        Utils.getFuture(futureRunnable);

        // executor service can also take callable and a value to return as a result
        List<Future<?>> list = IntStream.range(0, 10).mapToObj((count) -> {
            Future<String> futureSingle = executorService.submit(() -> {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Thread.currentThread().getName();
            });
            return futureSingle;
        }).collect(Collectors.toList());

        Utils.getFuture(list);

        System.out.println("Executor service has no pending tasks ----- ");
        executorService.shutdownNow();

        // Shutting down of executor service

        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello";
        };
        List<Callable<String>> callableList = Arrays.asList(callable, callable, callable, callable, callable);

//        ExecutorService executorService1 = Executors.newFixedThreadPool(2);
//        List<Callable<String>> callableList = Arrays.asList(callable, callable, callable, callable, callable);
//        List<Future<String>> result = executorService1.invokeAll(callableList);
//
//        // One way is executorService1.shutDown -> it'll finish all tasks and then shutdown.
//        executorService1.shutdown();
//        //Utils.getFuture(result, true);
//        System.out.println("executorService1 shut down");

        // Other way is to executorService1.shutDownNow() --> it'll shut without waiting
        ExecutorService executorService2 = Executors.newFixedThreadPool(2);
        List<Future<String>> resultNew = executorService2.invokeAll(callableList);
        List<Runnable> list1 = executorService2.shutdownNow();
        Utils.getFuture(resultNew, true);
        System.out.println("executorService2 shut down with "+list1.size()+" pending tasks");

        // Other is to do both
        ExecutorService executorService3 = Executors.newFixedThreadPool(2);
        List<Future<String>> result2 = executorService3.invokeAll(callableList);
        executorService3.shutdownNow();
        if (!executorService3.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            List<Runnable> list2= executorService3.shutdownNow();
            System.out.println("executorService3 shut down with"+ list2.size()+" pending tasks");
        }else {
            System.out.println("executorService3 shut down");
        }
        //Utils.getFuture(result2, true);


    }
}
