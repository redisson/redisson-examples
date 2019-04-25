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
package org.redisson.example.objects;

import java.util.concurrent.CountDownLatch;

import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

public class RateLimiterExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RRateLimiter limiter = redisson.getRateLimiter("myLimiter");
        // one permit per 2 seconds
        limiter.trySetRate(RateType.OVERALL, 1, 2, RateIntervalUnit.SECONDS);
        
        CountDownLatch latch = new CountDownLatch(2);
        limiter.acquire(1);
        latch.countDown();

        Thread t = new Thread(() -> {
            limiter.acquire(1);
            
            latch.countDown();
        });
        t.start();
        t.join();
        
        latch.await();
        
        redisson.shutdown();
    }
    
}
