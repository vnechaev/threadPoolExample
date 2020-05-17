package queues;


import java.time.LocalDateTime;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueExample {
    static class MyThread extends Thread {
        LocalDateTime creation;

        public LocalDateTime getCreation() {
            return creation;
        }

        public MyThread() {
            super();
            creation = LocalDateTime.now();
            System.out.println("Created...Thread " + creation.toString());
        }

        @Override
        public void run() {
            System.out.println("Running..." + "Thread " + Thread.currentThread().getName() + " I was created " + creation.toString());
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Died..." + creation);
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                35,
                50,
                10000,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );

//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 1; i < 30; i++) {
            pool.execute(new MyThread());
        }
    }
}
