package cn.hbwy.ftptohdfs.ftpdeal;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 实现了一个FTPClient连接池
 *
 * @author heaven
 */
public class FTPClientPool implements ObjectPool {
    private static final int DEFAULT_POOL_SIZE = 16;
    private final ArrayBlockingQueue<FTPClient> pool;
    private final FtpClientFactory factory;

    /**
     * 初始化连接池，需要注入一个工厂来提供FTPClient实例
     *
     * @param factory
     * @throws Exception
     */
    public FTPClientPool(FtpClientFactory factory) throws Exception {
        this(DEFAULT_POOL_SIZE, factory);
    }

    /**
     * @param poolSize:maxPoolsize
     * @param factory
     * @throws Exception
     */
    public FTPClientPool(int poolSize, FtpClientFactory factory) throws Exception {
        this.factory = factory;
        pool = new ArrayBlockingQueue<FTPClient>(poolSize * 2);
        initPool(poolSize);
    }

    /**
     * 初始化连接池，需要注入一个工厂来提供FTPClient实例
     *
     * @param maxPoolSize
     * @throws Exception
     */
    private void initPool(int maxPoolSize) throws Exception {
        for (int i = 0; i < maxPoolSize; i++) {
            //往池中添加对象
            addObject();
        }

    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.ObjectPool#borrowObject()
     */
    @Override
    public FTPClient borrowObject() throws Exception {
        FTPClient client = pool.take();
        if (client == null) {
            client = factory.makeObject(pool);
            addObject();
        } else if (!factory.validateObject(client)) {//验证不通过
            //使对象在池中失效
            invalidateObject(client);
            //制造并添加新对象到池中
            client = factory.makeObject(pool);
            addObject();
        }
        return client;

    }

    @Override
    public void returnObject(Object o) throws Exception {
        FTPClient client = (FTPClient) o;
        if ((client != null) && !pool.offer(client, 3, TimeUnit.SECONDS)) {

            factory.destroyObject(client);

        }
    }

    @Override
    public void invalidateObject(Object o) throws Exception {
        FTPClient client = (FTPClient) o;
        pool.remove(client);
    }


    /* (non-Javadoc)
     * @see org.apache.commons.pool.ObjectPool#addObject()
     */
    public void addObject() throws Exception {
        //插入对象到队列
        pool.offer(factory.makeObject(pool), 3, TimeUnit.SECONDS);
    }

    public int getNumIdle() throws UnsupportedOperationException {
        return 0;
    }

    public int getNumActive() throws UnsupportedOperationException {
        return 0;
    }

    public void clear() {

    }

    /* (non-Javadoc)
     * @see org.apache.commons.pool.ObjectPool#close()
     */
    public void close() throws Exception {
        while (pool.iterator().hasNext()) {
            FTPClient client = pool.take();
            factory.destroyObject(client);
        }
    }

    public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {

    }
}

