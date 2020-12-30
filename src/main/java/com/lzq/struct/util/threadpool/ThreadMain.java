package com.lzq.struct.util.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadMain {


    /**
     * <!-- 线程池 -->
     * <bean id="taskMqExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor" >
     *    <!-- 核心线程数  -->
     * <property name="corePoolSize" value="10" />
     *    <!-- 最大线程数 -->
     * <property name="maxPoolSize" value="200" />
     *    <!-- 队列最大长度 -->
     * <property name="queueCapacity" value="500" />
     *    <!-- 线程池维护线程所允许的空闲时间 -->
     * <property name="keepAliveSeconds" value="5" />
     *    <!-- 线程池对拒绝任务(无线程可用)的处理策略 -->
     * <property name="rejectedExecutionHandler">
     *       <bean class="java.util.concurrent.ThreadPoolExecutor$DiscardPolicy" />
     *    </property>
     * </bean>
     *
     * @param args
     * @throws InterruptedException
     */


    public static void main(String[] args) throws InterruptedException {


        List<Future<String>> results = new ArrayList<Future<String>>();

        /**
         *  FixedThreadPool 的mamximumPoolSize 永远不生效
         * SingleThreadPool 的mamximumPoolSize 永远不生效
         */
//        ExecutorService exec = Executors.newFixedThreadPool(2);
//        ExecutorService exec = Executors.newSingleThreadExecutor();


        /**
         * CachedThreadPool 队列内不存东西，直接创建线程解决。直到创建到最大线程数
         */
//        ExecutorService exec = Executors.newCachedThreadPool();

        /**
         * DelayedWorkQueue 是无界队列, 基于数组实现
         */
//        ExecutorService exec = new ScheduledThreadPoolExecutor(2);



        int corePoolSize = 4;
        int maximumPoolSize = 20;
        long keepAliveTime = 60L;
        TimeUnit unit = TimeUnit.SECONDS;
        LinkedBlockingQueue workQueue = new LinkedBlockingQueue(2);
//        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(13);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

//        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ExecutorService exec = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                handler);



        for (int i = 0; i < 35; i++) {

            Task task = new Task(i,workQueue);
            Future future = exec.submit(task);

            results.add(future);
        }


        exec.shutdown();

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



    static class Task implements Callable {

        private int majorKey;
        private BlockingQueue blockingQueue;

        public Task(int majorKey, BlockingQueue blockingQueue) {
            this.majorKey = majorKey;
            this.blockingQueue = blockingQueue;
        }

        @Override
        public String call() throws Exception {
            while (true){

                String str = String.format("thread id is %s,thread name is %s sleep 5s,num is %s,blockingQueue size is %s", Thread.currentThread().getId(), Thread.currentThread().getName(),majorKey,blockingQueue.size());
                System.out.println(str);
                TimeUnit.SECONDS.sleep(200);
            }

//            return String.format("thread id is %s,thread name is %s sleep 5s,num is %s,blockingQueue size is %s",Thread.currentThread().getId(),Thread.currentThread().getName(),majorKey,blockingQueue.size());
        }
    }
}
