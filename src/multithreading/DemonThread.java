package multithreading;

public class DemonThread extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +" is demon thread? "+Thread.currentThread().isDaemon());
        if(Thread.currentThread().getName()=="thread 2"){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName() +" completed");
    }

    public static void main(String[] args) {
        DemonThread demonThread1=new DemonThread();
        demonThread1.setName("thread 1");
        DemonThread demonThread2=new DemonThread();
        demonThread2.setName("thread 2");
        DemonThread demonThread3=new DemonThread();
        demonThread3.setName("thread 3");
        //Thread.currentThread().setDaemon(true);
        demonThread2.setDaemon(true);
        demonThread3.setDaemon(true);
        demonThread1.start();
        demonThread2.start();
        demonThread3.start();

    }
}


