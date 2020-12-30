package com.lzq.struct.util.zk;

/**
 *
 * @author lzq
 * */
public interface Lock {

    /**
     * 加锁
     *
     * （1）如果锁空间的根节点不存在，首先创建Znode根节点。这里假设为“/test/lock”。这个根节点，代表了一把分布式锁。
     * （2）客户端如果需要占用锁，则在“/test/lock”下创建临时的且有序的子节点。
     * @return
     * @throws Exception
     */
    boolean lock() throws Exception;

    /**
     * 解锁
     *
     * @return
     */
    boolean unlock();
}


