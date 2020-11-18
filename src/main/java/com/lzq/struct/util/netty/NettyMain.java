package com.lzq.struct.util.netty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NettyMain {


    public static void main(String[] args) {

//        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(null,true,12);
//
//        ByteBuf byteBuf = null;


//        FutureService


        List<Future<String>> results = new ArrayList<>();

//        ExecutorService exec = Executors.newCachedThreadPool();


        int corePoolSize = 3;
        int maximumPoolSize = 4;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
//        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue(3);
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(18);
//        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ExecutorService exec = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit, workQueue,
                handler);


        for (int i = 0; i < 20; i++) {

            Task task = new Task(i,workQueue);

            Future future = exec.submit(task);

            results.add(future);
        }

        for (Future<String> item:results) {
            try {
                String str = item.get();
                System.out.println(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


    }


    static class Task implements Callable{

        private int majorKey;
        private BlockingQueue blockingQueue;

        public Task(int majorKey,BlockingQueue blockingQueue) {
            this.majorKey = majorKey;
            this.blockingQueue = blockingQueue;
        }

        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(5);

            return String.format("%s sleep 5s,num is %s,blockingQueue size is %s",Thread.currentThread().getName(),majorKey,blockingQueue.size());
        }
    }

}
