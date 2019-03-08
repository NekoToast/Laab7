import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreLock {
    static Semaphore semaphore = new Semaphore(3);
    Random generator = new Random();

    public void  useComputer(String threadName){
        try {
            semaphore.acquire();
            System.out.println("Client is using computer: " + threadName);
            int i = generator.nextInt(1000);
            Thread.sleep(i);
            System.out.println(threadName+" ended using computer");
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final SemaphoreLock semaphoreLock = new SemaphoreLock();
        Runnable customerThread = new Runnable() {
            public void run() {
                try {
                    while(true) {
                        Thread.sleep(200);
                        String customerName = Thread.currentThread().getName();
                        System.out.println(customerName + " tries to use computer, available computers: "+semaphore.availablePermits());

                        semaphoreLock.useComputer(customerName);
                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(customerThread, "Customer1");
        Thread t2 = new Thread(customerThread, "Customer2");
        Thread t3 = new Thread(customerThread, "Customer3");
        Thread t4 = new Thread(customerThread, "Customer4");
        Thread t5 = new Thread(customerThread, "Customer5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
