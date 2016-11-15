/**
 * Copyright 2016 Nikita Koksharov
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
package org.redisson.example.collections;

import org.redisson.Redisson;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RedissonClient;

public class BoundedBlockingQueueExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RBoundedBlockingQueue<String> queue = redisson.getBoundedBlockingQueue("myQueue");
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        
        queue.trySetCapacity(5);
        
        Thread t = new Thread(() -> {
            try {
                String element = queue.take();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t.start();
        
        queue.put("6");
        
        redisson.shutdown();
    }
    
}
