package hookMethods;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Illustrates weakness of afterExecute() method
 */
public class AfterExecuteFalls {

    public static void main(String[] args) {
        class MyThreadPool extends ThreadPoolExecutor {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
//                throw new RuntimeException("Error in beforeExecute()");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                //Not executed if beforeExecute throw Exception
                super.afterExecute(r, t);
                if (t != null) {
                    System.out.println("After execute catch exception " + t.getClass());
                }
            }

            public MyThreadPool() {
                super(5,
                        5,
                        10000,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue(30));
            }
        }

        ThreadPoolExecutor threadPool = new MyThreadPool();
//        threadPool.setThreadFactory(new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread t = new Thread(r);
//                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//                    @Override
//                    public void uncaughtException(Thread t, Throwable e) {
//                        System.out.println(t.getName() + "-UncaughtExceptionHandler catch exception - " + e.getClass() + " Message - " + e.getMessage());
//                    }
//                });
//                return t;
//            }
//        });

//        threadPool.execute(() -> System.out.println("hello, world!"));
//        threadPool.execute(() -> System.out.println(5 / 0));
//        threadPool.submit(() -> System.out.println(5 / 0));
        threadPool.execute(() -> {throw new RuntimeException("Error in executed Thread");});
//        threadPool.execute(() -> {throw new OutOfMemoryError("Error in executed Thread");});
//        threadPool.shutdown();
    }
}
