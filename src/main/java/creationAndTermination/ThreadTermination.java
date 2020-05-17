package creationAndTermination;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTermination {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                10,
                500,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue()
        );
//        pool.allowCoreThreadTimeOut(true);
        pool.execute(() -> System.out.println("Hello"));
    }
}
