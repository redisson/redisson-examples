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

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;

public class PermitExpirableSemaphoreExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RPermitExpirableSemaphore s = redisson.getPermitExpirableSemaphore("test");
        s.trySetPermits(1);
        String permitId = s.tryAcquire(100, 2, TimeUnit.SECONDS);

        Thread t = new Thread() {
            public void run() {
                RPermitExpirableSemaphore s = redisson.getPermitExpirableSemaphore("test");
                try {
                    String permitId = s.acquire();
                    s.release(permitId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };

        t.start();
        t.join();
        
        s.tryRelease(permitId);
        
    }
    
}
