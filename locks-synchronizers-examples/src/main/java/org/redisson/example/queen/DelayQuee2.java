package org.redisson.example.queen;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DelayQuee2 {


    public static void s(){
        RedissonClient redissonClient = Redisson.create();
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("delayQuee");
//        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
//        delayedQueue.offer("222",10, TimeUnit.SECONDS);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        String take = blockingQueue.take();
                        System.out.println("[2222]queue take  data= " + take);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }




    public static void main(String[] args) {
        s();
    }
}
