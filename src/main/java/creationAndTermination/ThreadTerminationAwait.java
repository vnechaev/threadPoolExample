package creationAndTermination;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTerminationAwait {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                10,
                2000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue()
        );
        pool.allowCoreThreadTimeOut(true);
        pool.execute(() -> System.out.println("Hello"));

        System.out.println(pool.awaitTermination(1000, TimeUnit.MILLISECONDS));    }
}
