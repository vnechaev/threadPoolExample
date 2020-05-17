package saturationPolicy;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;
    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }
    public void submitTask(final Runnable command)
            throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
    static class MyThreadPool extends ThreadPoolExecutor {
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


    public static void main(String[] args) throws InterruptedException {
        MyThreadPool poool = new MyThreadPool();
        poool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        BoundedExecutor executotr = new BoundedExecutor(new MyThreadPool(), 2);
        for (int i = 1; i < 20; i++) {
            executotr.submitTask(new SaturationPolicyExamples.MyThread());
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