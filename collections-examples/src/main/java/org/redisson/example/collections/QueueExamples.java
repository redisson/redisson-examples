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
package org.redisson.example.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;

public class QueueExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RQueue<String> queue = redisson.getQueue("myQueue");
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        
        queue.contains("1");
        queue.peek();
        queue.poll();
        queue.element();
        
        for (String string : queue) {
            // iteration through bulk loaded values
        }
        
        boolean removedValue = queue.remove("1");
        queue.removeAll(Arrays.asList("1", "2", "3"));
        queue.containsAll(Arrays.asList("4", "1", "0"));
        
        List<String> secondList = new ArrayList<>();
        secondList.add("4");
        secondList.add("5");
        queue.addAll(secondList);

        RQueue<String> secondQueue = redisson.getQueue("mySecondQueue");
        
        queue.pollLastAndOfferFirstTo(secondQueue.getName());
        
        redisson.shutdown();
    }
    
}
