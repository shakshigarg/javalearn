package multithreading;

class Thread2 implements Runnable{

    @Override
    public void run() {
        System.out.println("thread2 : "+ Thread.currentThread().getId());
        System.out.println("thread2 Priority : "+ Thread.currentThread().getPriority());
        System.out.println(
                "State of thread1 after calling .sleep() method on it - "
                        + TestThreadStates.thread1.getState());
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("State of thread1 while it calls join method on thread2 :"+TestThreadStates.thread1.getState());
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

public class TestThreadStates implements Runnable {
    public static Thread thread1;
    public static TestThreadStates obj;

    public static void main(String[] args) throws InterruptedException {
        // running on main thread

        System.out.println("Main thread : "+ Thread.currentThread().getId());
        System.out.println("Main thread Priority : "+ Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(10);
        obj = new TestThreadStates();
        thread1 = new Thread(obj);
        System.out.println("Thread1 state after creating : "+thread1.getState());
        thread1.start();
        System.out.println("Thread1 state after starting : "+thread1.getState());
        Thread.sleep(1000);
        System.out.println("Thread1 state after everything is done : "+thread1.getState());

    }

    @Override
    public void run() {
        System.out.println("thread1 : "+ Thread.currentThread().getId());
        System.out.println("thread1 Priority : "+ Thread.currentThread().getPriority());
        Thread.currentThread().setPriority(10);
        Thread2 thread2Obj=new Thread2();
        Thread thread2=new Thread(thread2Obj);
        System.out.println("State of thread 2 just after creating it : "+thread2.getState());
        thread2.start();
        System.out.println("State of thread2 after calling .start() method on it - " + thread2.getState());

        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(
                "State of thread2 after calling .sleep() method on it - "
                        + thread2.getState());

        try {
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(
                "State of thread2 after calling .join() method on it - "
                        + thread2.getState());

    }
}
