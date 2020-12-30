package com.lzq.struct.util.jobs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JDKJob {


    /**定时任务
     * @param args
     */
    public static void main(String[] args) {

        TaskInJob taskInJob = new TaskInJob();

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        // 1表示时间单位的数值 TimeUnit.SECONDS  延时单位为秒
        service.scheduleAtFixedRate(taskInJob, 0, 1, TimeUnit.SECONDS);

    }

    public static class TaskInJob implements Runnable{

        int i = 1;

        @Override
        public void run() {
            try {
                System.out.println(i++);
                if (i == 5) {
                    int a = 1 / 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

