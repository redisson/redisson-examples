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

import java.util.concurrent.ExecutionException;

import org.redisson.Redisson;
import org.redisson.api.BatchOptions;
import org.redisson.api.BatchResult;
import org.redisson.api.RBatch;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;

public class BatchExamples {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RBatch batch = redisson.createBatch(BatchOptions.defaults());
        batch.getMap("test1").fastPutAsync("1", "2");
        batch.getMap("test2").fastPutAsync("2", "3");
        batch.getMap("test3").putAsync("2", "5");
        RFuture<Long> future = batch.getAtomicLong("counter").incrementAndGetAsync();
        batch.getAtomicLong("counter").incrementAndGetAsync();

        // result could be acquired through RFuture object returned by batched method
        // or 
        // through result list by corresponding index
        future.whenComplete((res, exception) -> {
            // ...
        });

        BatchResult<?> res = batch.execute();
        Long counter = (Long) res.getResponses().get(3);
        
        future.get().equals(counter);
        
        redisson.shutdown();
    }
    
}
