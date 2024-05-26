package concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

class Data {
    String packet;
    volatile boolean transfer = false;

    public synchronized void sendPacket(String packet) throws InterruptedException {
        while (transfer) {
            this.wait();
        }
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.packet = packet;
        System.out.println("Packet sent " + packet);
        transfer = true;
        this.notifyAll();
    }

    public synchronized String receivePacket() throws InterruptedException {
        while (!transfer) {
            this.wait();
        }
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(packet + " received");
        String p = packet;
        transfer = false;
        this.notifyAll();
        return p;
    }
}

class Sender implements Runnable {
    Data data;

    public Sender(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        String a = "a", b = "b", end = "end";
        ArrayList<String> packets = new ArrayList<>(Arrays.asList(a, b, "c", "d", end));

        packets.forEach((packet) -> {
            try {
                data.sendPacket(packet);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}

class Receiver implements Runnable {
    Data data;

    public Receiver(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (data.receivePacket() != "end") {

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class WaitAndNotify {
    public static void main(String[] args) {
        Data data = new Data();
        Sender sender = new Sender(data);
        Receiver receiver = new Receiver(data);

        Thread senderThread = new Thread(sender);
        Thread recThread = new Thread(receiver);

        senderThread.start();
        recThread.start();
    }
}
