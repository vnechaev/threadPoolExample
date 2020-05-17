package creationAndTermination;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadCreation {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                10,
                10000,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue(10)
//                new LinkedBlockingQueue()
        );

        for (int i = 0; i < 500; i++) {
            pool.execute(() -> {
                while (true) {
                }
            });
            System.out.println("Thread executed by pool " + i);
            System.out.println("Threads in pool " + pool.getActiveCount());

        }
        Thread.sleep(2000);
        System.out.println("Threads in pool " + pool.getActiveCount());
    }
}
