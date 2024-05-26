package multithreading;

public class MainThreadDeadlock {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Entering deadlock--");
        Thread.currentThread().join();
        System.out.println("Will never execute");
    }
}
