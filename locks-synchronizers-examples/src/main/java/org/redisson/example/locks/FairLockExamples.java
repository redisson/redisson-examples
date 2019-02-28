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

import java.util.ArrayList;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class FairLockExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RLock lock = redisson.getFairLock("test");

        int size = 10;
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < size; i++) {
            final int j = i;
            Thread t = new Thread() {
                public void run() {
                    lock.lock();
                    lock.unlock();
                };
            };
            
            threads.add(t);
        }
        
        for (Thread thread : threads) {
            thread.start();
            thread.join(5);
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
    }
    
}
