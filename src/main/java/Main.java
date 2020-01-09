import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(4, 10, 100L, TimeUnit.HOURS,
                new ArrayBlockingQueue(300));

        for (int i = 0; i < 5; i++) {
            threadPool.execute(new Task());
        }

        for (int i = 0; i < 4; i++) {
            Thread.sleep(2000);
            System.out.println("Thread count " + threadPool.getActiveCount());
        }

        threadPool.execute(new Runnable() {
            public void run() {
                throw new ArithmeticException();
            }
        });
    }
}
