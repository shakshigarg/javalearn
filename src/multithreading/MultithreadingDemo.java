package multithreading;

class ThreadE extends Thread{
    public ThreadE() {
        System.out.println(Thread.currentThread().getState());
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread "+Thread.currentThread().getId()+" is running!");
        }catch (Exception e){
            System.out.println("Something went wrong!");
        }
    }
}

class ThreadI implements Runnable{
    public ThreadI() {
        System.out.println(Thread.currentThread().getState());
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread "+Thread.currentThread().getId()+" is running!");
            System.out.println(Thread.currentThread().getState());
        }catch (Exception e){
            System.out.println("Something went wrong!");
        }
    }
}

public class MultithreadingDemo {
    public static void main(String[] args) {
        for(int i=0;i<9;i++){
            ThreadE e =new ThreadE();
            e.start();
        }

        for(int i=0;i<9;i++){
            Thread e =new Thread(new ThreadI());
            e.start();
        }
        System.out.println(Thread.currentThread().getState());
    }
}
