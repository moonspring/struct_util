//package com.lzq.struct.util.zk;
//
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// *
// * @author lzq
// */
//public class ZkImpl implements Lock {
//
//
//    private AtomicInteger lockCount;
//
//    private Thread thread;
//
//    private String locked_path;
//
//    private String ZK_PATH;
//
//    private long WAIT_TIME;
//
//    private String prior_path;
//
////    @Override
////    public boolean lock() {
////
////        try {
////            boolean locked = false;
////            locked = tryLock();
////            if (locked) {
////                return true;
////            }
////            while (!locked) {
////                await();
////                if (checkLocked()) {
////                    locked=true;
////                }
////            }
////            return true;
////        } catch (Exception e) {
////            e.printStackTrace();
////            unlock();
////        }
////        return false;
////    }
//
//    private boolean tryLock() throws Exception {
//        //创建临时Znode
//        List<String> waiters = getWaiters();
//        locked_path = ZKclient.instance
//                .createEphemeralSeqNode(LOCK_PREFIX);
//        if (null == locked_path) {
//            throw new Exception("zk error");
//        }
//        locked_short_path = getShorPath(locked_path);
//
//        //获取等待的子节点列表，判断自己是否第一个
//        if (checkLocked()) {
//            return true;
//        }
//
//        // 判断自己排第几个
//        int index = Collections.binarySearch(waiters, locked_short_path);
//        if (index < 0) { // 网络抖动，获取到的子节点列表里可能已经没有自己了
//            throw new Exception("节点没有找到: " + locked_short_path);
//        }
//
//        //如果自己没有获得锁，则要监听前一个节点
//        prior_path = ZK_PATH + "/" + waiters.get(index - 1);
//
//        return false;
//    }
//
//    private boolean checkLocked() {
//        //获取等待的子节点列表
//
//        List<String> waiters = getWaiters();
//        //节点按照编号，升序排列
//        Collections.sort(waiters);
//
//        // 如果是第一个，代表自己已经获得了锁
//        if (locked_short_path.equals(waiters.get(0))) {
////            log.info("成功的获取分布式锁,节点为{}", locked_short_path);
//            return true;
//        }
//        return false;
//    }
//
//
//    private void await() throws Exception {
//
//        if (null == prior_path) {
//            throw new Exception("prior_path error");
//        }
//
//        final CountDownLatch latch = new CountDownLatch(1);
//
//
//        //订阅比自己次小顺序节点的删除事件
//        Watcher w = new Watcher() {
//            @Override
//            public void process(WatchedEvent watchedEvent) {
//                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
////                log.info("[WatchedEvent]节点删除");
//
//                latch.countDown();
//            }
//        };
//
//        client.getData().usingWatcher(w).forPath(prior_path);
//
//        latch.await(WAIT_TIME, TimeUnit.SECONDS);
//    }
//
//
//
////    修改前面的lock方法，在前面加上可重入的判断逻辑。代码如下：
//
//    @Override
//    public boolean lock() {
//        synchronized (this) {
//            if (lockCount.get() == 0) {
//                thread = Thread.currentThread();
//                lockCount.incrementAndGet();
//            } else {
//                if (!thread.equals(Thread.currentThread())) {
//                    return false;
//                }
//                lockCount.incrementAndGet();
//                return true;
//            }
//        }
//
//        //...
//    }
//
//
//    @Override
//    public boolean unlock() {
//
//        if (!thread.equals(Thread.currentThread())) {
//            return false;
//        }
//
//        int newLockCount = lockCount.decrementAndGet();
//
//        if (newLockCount < 0) {
//            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + locked_path);
//        }
//
//        if (newLockCount != 0) {
//            return true;
//        }
//        try {
//            if (ZKclient.instance.isNodeExist(locked_path)) {
//                client.delete().forPath(locked_path);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//
//    public void testzkMutex() throws InterruptedException {
//
//        CuratorFramework client=ZKclient.instance.getClient();
//        final InterProcessMutex zkMutex =
//                new InterProcessMutex(client,"/mutex");  ;
//        for (int i = 0; i < 10; i++) {
//            FutureTaskScheduler.add(() -> {
//
//                try {
//                    zkMutex.acquire();
//
//                    for (int j = 0; j < 10; j++) {
//
//                        count++;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("count = " + count);
//                    zkMutex.release();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }
//
//        Thread.sleep(Integer.MAX_VALUE);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
//package com.lzq.struct.util.zk;
//
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// *
// * @author lzq
// */
//public class ZkImpl implements Lock {
//
//
//    private AtomicInteger lockCount;
//
//    private Thread thread;
//
//    private String locked_path;
//
//    private String ZK_PATH;
//
//    private long WAIT_TIME;
//
//    private String prior_path;
//
////    @Override
////    public boolean lock() {
////
////        try {
////            boolean locked = false;
////            locked = tryLock();
////            if (locked) {
////                return true;
////            }
////            while (!locked) {
////                await();
////                if (checkLocked()) {
////                    locked=true;
////                }
////            }
////            return true;
////        } catch (Exception e) {
////            e.printStackTrace();
////            unlock();
////        }
////        return false;
////    }
//
//    private boolean tryLock() throws Exception {
//        //创建临时Znode
//        List<String> waiters = getWaiters();
//        locked_path = ZKclient.instance
//                .createEphemeralSeqNode(LOCK_PREFIX);
//        if (null == locked_path) {
//            throw new Exception("zk error");
//        }
//        locked_short_path = getShorPath(locked_path);
//
//        //获取等待的子节点列表，判断自己是否第一个
//        if (checkLocked()) {
//            return true;
//        }
//
//        // 判断自己排第几个
//        int index = Collections.binarySearch(waiters, locked_short_path);
//        if (index < 0) { // 网络抖动，获取到的子节点列表里可能已经没有自己了
//            throw new Exception("节点没有找到: " + locked_short_path);
//        }
//
//        //如果自己没有获得锁，则要监听前一个节点
//        prior_path = ZK_PATH + "/" + waiters.get(index - 1);
//
//        return false;
//    }
//
//    private boolean checkLocked() {
//        //获取等待的子节点列表
//
//        List<String> waiters = getWaiters();
//        //节点按照编号，升序排列
//        Collections.sort(waiters);
//
//        // 如果是第一个，代表自己已经获得了锁
//        if (locked_short_path.equals(waiters.get(0))) {
////            log.info("成功的获取分布式锁,节点为{}", locked_short_path);
//            return true;
//        }
//        return false;
//    }
//
//
//    private void await() throws Exception {
//
//        if (null == prior_path) {
//            throw new Exception("prior_path error");
//        }
//
//        final CountDownLatch latch = new CountDownLatch(1);
//
//
//        //订阅比自己次小顺序节点的删除事件
//        Watcher w = new Watcher() {
//            @Override
//            public void process(WatchedEvent watchedEvent) {
//                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
////                log.info("[WatchedEvent]节点删除");
//
//                latch.countDown();
//            }
//        };
//
//        client.getData().usingWatcher(w).forPath(prior_path);
//
//        latch.await(WAIT_TIME, TimeUnit.SECONDS);
//    }
//
//
//
////    修改前面的lock方法，在前面加上可重入的判断逻辑。代码如下：
//
//    @Override
//    public boolean lock() {
//        synchronized (this) {
//            if (lockCount.get() == 0) {
//                thread = Thread.currentThread();
//                lockCount.incrementAndGet();
//            } else {
//                if (!thread.equals(Thread.currentThread())) {
//                    return false;
//                }
//                lockCount.incrementAndGet();
//                return true;
//            }
//        }
//
//        //...
//    }
//
//
//    @Override
//    public boolean unlock() {
//
//        if (!thread.equals(Thread.currentThread())) {
//            return false;
//        }
//
//        int newLockCount = lockCount.decrementAndGet();
//
//        if (newLockCount < 0) {
//            throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + locked_path);
//        }
//
//        if (newLockCount != 0) {
//            return true;
//        }
//        try {
//            if (ZKclient.instance.isNodeExist(locked_path)) {
//                client.delete().forPath(locked_path);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//
//
//    public void testzkMutex() throws InterruptedException {
//
//        CuratorFramework client=ZKclient.instance.getClient();
//        final InterProcessMutex zkMutex =
//                new InterProcessMutex(client,"/mutex");  ;
//        for (int i = 0; i < 10; i++) {
//            FutureTaskScheduler.add(() -> {
//
//                try {
//                    zkMutex.acquire();
//
//                    for (int j = 0; j < 10; j++) {
//
//                        count++;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("count = " + count);
//                    zkMutex.release();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }
//
//        Thread.sleep(Integer.MAX_VALUE);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
