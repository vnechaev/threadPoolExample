import java.util.concurrent.*;

public class ThreadPool extends ThreadPoolExecutor {

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("Enter afterExecute()");
        if (t instanceof NullPointerException) {
            execute(r);
            System.out.println("restart finished task");
        }
        if (t instanceof ArithmeticException) {
            System.out.println("App is fall due to ArithmeticException");
            shutdown();
        }
    }
}
