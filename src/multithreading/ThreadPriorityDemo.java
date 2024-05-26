package multithreading;

class ThreadNew implements Runnable{
    @Override
    public void run() {
        System.out.println("Executing " +Thread.currentThread().getName());
    }
}

public class ThreadPriorityDemo {
    public static void main(String[] args) {
        Thread thread1=new Thread((new ThreadNew()));
        thread1.setName("Thread 1");
        Thread thread2=new Thread((new ThreadNew()));
        thread2.setName("Thread 2");
        Thread thread3=new Thread((new ThreadNew()));
        thread3.setName("Thread 3");

        thread2.setPriority(10);
        thread1.setPriority(5);
        thread3.setPriority(1);
        thread2.start();
        thread1.start();
        thread3.start();
    }
}
