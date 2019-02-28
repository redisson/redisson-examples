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

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RSetCache;
import org.redisson.api.RedissonClient;

public class SetCacheExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RSetCache<String> setCache = redisson.getSetCache("mySet");

        // with ttl = 20 seconds
        boolean isAdded = setCache.add("1", 20, TimeUnit.SECONDS);
        // store value permanently
        setCache.add("2");
        
        setCache.contains("1");
        
        for (String string : setCache) {
            // iteration through bulk loaded values
        }
        
        boolean removedValue = setCache.remove("1");
        setCache.removeAll(Arrays.asList("1", "2", "3"));
        setCache.containsAll(Arrays.asList("4", "1", "0"));
        
        RSet<String> secondsSet = redisson.getSet("mySecondsSet");
        secondsSet.add("4");
        secondsSet.add("5");

        Set<String> allValues = secondsSet.readAll();
        
        redisson.shutdown();
    }
    
}
