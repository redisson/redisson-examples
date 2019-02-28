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

import org.redisson.Redisson;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RedissonClient;

public class AtomicDoubleExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RAtomicDouble atomicDouble = redisson.getAtomicDouble("myDouble");
        atomicDouble.getAndDecrement();
        atomicDouble.getAndIncrement();
        
        atomicDouble.addAndGet(10.323);
        atomicDouble.compareAndSet(29.4, 412.91);
        
        atomicDouble.decrementAndGet();
        atomicDouble.incrementAndGet();
        
        atomicDouble.getAndAdd(302.00);
        atomicDouble.getAndDecrement();
        atomicDouble.getAndIncrement();
        
        redisson.shutdown();
    }
    
}
