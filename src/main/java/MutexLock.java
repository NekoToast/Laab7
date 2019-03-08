import java.util.concurrent.Semaphore;

public class MutexLock {
    static Semaphore semaphore = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);

    public void  useComputer(String threadName){
            try {
                    mutex.acquire();
                    System.out.println("Client is using computer: " + threadName);
                    Thread.sleep(1500);
                    System.out.println(threadName+" ended using computer");
                    mutex.release();
                    semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        final MutexLock mutexLock = new MutexLock();
        Runnable customerThread = new Runnable() {
            public void run() {
                try {
                    while(true) {
                        Thread.sleep(50);
                        String customerName = Thread.currentThread().getName();
                        System.out.println(customerName + " tries to use computer");
                        semaphore.acquire();

                        mutexLock.useComputer(customerName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        semaphore.release();    // aby zawsze był dostępny 1 permit
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
