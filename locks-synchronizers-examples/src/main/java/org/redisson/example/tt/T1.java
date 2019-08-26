package org.redisson.example.tt;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class T1 {

    RedissonClient redisson = Redisson.create();

    public void s() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                RLock lock = redisson.getLock("lock1");
                System.out.println("getlock end ,begin lock()");
                lock.lock();
                System.out.println("lock() end");
            }
        });
//        lock.unlock();
        t.start();
//        t.join();
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.s();
    }

}
