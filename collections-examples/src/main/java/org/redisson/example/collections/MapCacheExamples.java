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

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

public class MapCacheExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RMapCache<String, Integer> mapCache = redisson.getMapCache("test");
        
        // with ttl = 10 seconds
        Integer prevValue = mapCache.put("1", 10, 10, TimeUnit.SECONDS);
        // with ttl = 15 seconds and maxIdleTime = 5 seconds
        Integer prevValue2 = mapCache.put("2", 20, 15, TimeUnit.SECONDS, 5, TimeUnit.SECONDS);
        // store value permanently 
        Integer prevValue3 = mapCache.put("3", 30);
        
        // with ttl = 30 seconds
        Integer currValue = mapCache.putIfAbsent("4", 40, 30, TimeUnit.SECONDS);
        // with ttl = 40 seconds and maxIdleTime = 10 seconds
        Integer currValue2 = mapCache.putIfAbsent("5", 50, 40, TimeUnit.SECONDS, 10, TimeUnit.SECONDS);
        // try to add new key-value permanently 
        Integer currValue3 = mapCache.putIfAbsent("6", 60);
        
        // use fast* methods when previous value is not required

        // with ttl = 20 seconds
        boolean isNewKey1 = mapCache.fastPut("7", 70, 20, TimeUnit.SECONDS);
        // with ttl = 40 seconds and maxIdleTime = 20 seconds
        boolean isNewKey2 = mapCache.fastPut("8", 80, 40, TimeUnit.SECONDS, 20, TimeUnit.SECONDS);
        // store value permanently 
        boolean isNewKey3 = mapCache.fastPut("9", 90);
        
        // try to add new key-value permanently
        boolean isNewKeyPut = mapCache.fastPutIfAbsent("10", 100);

        boolean contains = mapCache.containsKey("a");
        
        Integer value = mapCache.get("c");
        Integer updatedValue = mapCache.addAndGet("a", 32);
        
        Integer valueSize = mapCache.valueSize("c");
        
        Set<String> keys = new HashSet<String>();
        keys.add("a");
        keys.add("b");
        keys.add("c");
        Map<String, Integer> mapSlice = mapCache.getAll(keys);
        
        // use read* methods to fetch all objects
        Set<String> allKeys = mapCache.readAllKeySet();
        Collection<Integer> allValues = mapCache.readAllValues();
        Set<Entry<String, Integer>> allEntries = mapCache.readAllEntrySet();
        
        redisson.shutdown();
    }
    
}
