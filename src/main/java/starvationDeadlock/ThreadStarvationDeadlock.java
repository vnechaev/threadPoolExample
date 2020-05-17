package starvationDeadlock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadStarvationDeadlock {
//    ExecutorService exec = Executors.newSingleThreadExecutor();
    ExecutorService exec = Executors.newFixedThreadPool(1);

    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return fileName + "processed";
        }

    }

    public class RenderPageTask implements Callable<String> {

        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            return "body";
        }
    }

    public void processPage() throws Exception {
        Future<String> wholePage = exec.submit(new RenderPageTask());
        System.out.println("Content of whole page is " + wholePage.get());
    }

    public static void main(String[] st) throws Exception {
        ThreadStarvationDeadlock tdl = new ThreadStarvationDeadlock();
        tdl.processPage();

    }
}
