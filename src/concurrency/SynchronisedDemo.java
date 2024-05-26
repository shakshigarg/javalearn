package concurrency;

import java.util.concurrent.locks.ReentrantLock;

class IncrementWithVolatile {
    volatile int count = 0;

    public void incCount() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class IncWithRetLock {
    final ReentrantLock reentrantLock = new ReentrantLock();
    int count = 0;

    public void incCount() {
        reentrantLock.lock();
        try {
            count++;
        } finally {
            reentrantLock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}

class IncrementWithoutSync {
    int count = 0;

    /*Comment uncomment this line to check output*/
    public void incCount() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

class IncrementWithSync {
    int count = 0;

    /*Comment uncomment this line to check output*/
    public synchronized void incCount() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
class IncrementWithSyncBlock {
    int count = 0;

    /*Comment uncomment this line to check output*/
    public void incCount() {
        synchronized (this){
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}

class ThreadImpl implements Runnable {
    IncrementWithoutSync incrementWithoutSync;
    IncrementWithSync incrementWithSync;
    IncrementWithVolatile incrementWithVolatile;
    IncWithRetLock incWithRetLock;
    IncrementWithSyncBlock incrementWithSyncBlock;

    @Override
    public void run() {

        try {

            for (int i = 0; i < 4; i++) {
                Thread.sleep(10);
                incrementWithoutSync.incCount();
                Thread.sleep(1);
                incrementWithSync.incCount();
                Thread.sleep(5);
                incrementWithVolatile.incCount();
                Thread.sleep(1);
                incWithRetLock.incCount();
                incrementWithSyncBlock.incCount();
            }
            //System.out.println("Count as per thread without sync obj " + Thread.currentThread().getName() + " " + incrementWithoutSync.getCount());
            //System.out.println("Count as per thread with sync obj " + Thread.currentThread().getName() + " " + incrementWithSync.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ThreadImpl(IncrementWithoutSync incrementWithoutSync, IncrementWithSync incrementWithSync, IncrementWithVolatile incrementWithVol, IncWithRetLock incWithRetLock, IncrementWithSyncBlock incrementWithSyncBlock) {
        this.incrementWithoutSync = incrementWithoutSync;
        this.incrementWithSync = incrementWithSync;
        this.incrementWithVolatile = incrementWithVol;
        this.incWithRetLock = incWithRetLock;
        this.incrementWithSyncBlock = incrementWithSyncBlock;
    }
}

public class SynchronisedDemo {
    public static void main(String[] args) throws InterruptedException {
        IncrementWithoutSync incrementWithoutSync = new IncrementWithoutSync();
        IncrementWithSync incrementWithSync = new IncrementWithSync();
        IncrementWithVolatile incrementWithVol = new IncrementWithVolatile();
        IncWithRetLock incWithRetLock = new IncWithRetLock();
        IncrementWithSyncBlock incrementWithSyncBlock = new IncrementWithSyncBlock();
        ThreadImpl thread1Obj = new ThreadImpl(incrementWithoutSync, incrementWithSync, incrementWithVol, incWithRetLock, incrementWithSyncBlock);
        ThreadImpl thread2Obj = new ThreadImpl(incrementWithoutSync, incrementWithSync, incrementWithVol, incWithRetLock, incrementWithSyncBlock);

        Thread thread1 = new Thread(thread1Obj);
        Thread thread2 = new Thread(thread2Obj);
        thread1.setName("thread1");
        thread2.setName("thread2");
        thread1.start();
        thread2.start();
        Thread.sleep(200);
        System.out.println("Count as per thread without sync obj " + Thread.currentThread().getName() + " " + incrementWithoutSync.getCount());
        System.out.println("Count as per thread with sync obj " + Thread.currentThread().getName() + " " + incrementWithSync.getCount());
        System.out.println("Count as per thread with volatile obj " + Thread.currentThread().getName() + " " + incrementWithVol.getCount());
        System.out.println("Count as per thread with reentrantLock obj " + Thread.currentThread().getName() + " " + incWithRetLock.getCount());
        System.out.println("Count as per thread with syn block  " + Thread.currentThread().getName() + " " + incrementWithSyncBlock.getCount());
    }
}