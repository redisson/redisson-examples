/**
 * Copyright (c) 2016-2019 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.example.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;

public class CountDownLatchExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        final RCountDownLatch latch = redisson.getCountDownLatch("latch1");
        latch.trySetCount(1);

        executor.execute(new Runnable() {

            @Override
            public void run() {
                latch.countDown();
            }
            
        });

        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    latch.await(550, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        });

        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

    }
    
}
