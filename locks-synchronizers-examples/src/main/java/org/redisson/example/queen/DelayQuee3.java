package org.redisson.example.queen;

import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class DelayQuee3 {


    public static void s(){
        RedissonClient redissonClient = Redisson.create();
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("delayQuee");
        for(int i =0;i<100;i++){
            RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
            delayedQueue.offer(i+"",10, TimeUnit.SECONDS);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true){
//                        String take = blockingQueue.take();
//                        System.out.println("[2222]queue take  data= " + take);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }




    public static void main(String[] args) {
        s();
    }
}
