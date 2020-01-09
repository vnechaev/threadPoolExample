public class Task implements Runnable {
    public void run() {
        System.out.println("My task is started running...");
        try {
            System.out.println("sleep");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("throw Exception");
        throw new NullPointerException();
    }
}
