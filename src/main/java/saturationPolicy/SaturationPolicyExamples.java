package saturationPolicy;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class SaturationPolicyExamples {
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

    public static void main(String[] args) throws InterruptedException {

        class MyThreadPool extends ThreadPoolExecutor {
            public MyThreadPool() {
                super(
                        1,
                        1,
                        10000,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue(2)
                );
            }
        }


        MyThreadPool pool = new MyThreadPool();
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i < 20; i++) {
            MyThread thread = new MyThread();
            pool.execute(thread);
            Thread.sleep(100);
//            if (i == 10) {
//                pool.shutdown();
//                System.out.println();
//                System.out.println("Last before Shutdown  " + thread.getCreation().toString());
//                System.out.println("Shutdown!");
//            }
        }
    }
}

