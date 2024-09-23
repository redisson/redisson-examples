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

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.options.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;
import org.redisson.api.options.LocalCachedMapOptions.EvictionPolicy;
import org.redisson.Redisson;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.options.LocalCachedMapOptions.ExpirationEventPolicy;
import org.redisson.api.options.LocalCachedMapOptions.SyncStrategy;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

public class LocalCachedMapExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        // NOTE: Key and Value can be of any type, eg. Object,Integer,Boolean etc.
        final LocalCachedMapOptions<String, Integer> options = LocalCachedMapOptions.<String, Integer>name("myMap")
        .cacheSize(10000)
        .maxIdle(Duration.ofSeconds(60))
        .timeToLive(Duration.ofSeconds(60))
        .evictionPolicy(EvictionPolicy.LFU)
        .syncStrategy(SyncStrategy.UPDATE)
        .expirationEventPolicy(ExpirationEventPolicy.SUBSCRIBE_WITH_KEYSPACE_CHANNEL);

        // Create a Local Cached Map with options
        RLocalCachedMap<String, Integer> cachedMap = redisson.getLocalCachedMap(options);
        cachedMap.put("a", 1);
        cachedMap.put("b", 2);
        cachedMap.put("c", 3);
        
        boolean contains = cachedMap.containsKey("a");
        
        Integer value = cachedMap.get("c");
        Integer updatedValue = cachedMap.addAndGet("a", 32);
        
        Integer valueSize = cachedMap.valueSize("c");
        
        Set<String> keys = new HashSet<String>();
        keys.add("a");
        keys.add("b");
        keys.add("c");
        Map<String, Integer> mapSlice = cachedMap.getAll(keys);
        
        // use read* methods to fetch all objects
        Set<String> allKeys = cachedMap.readAllKeySet();
        Collection<Integer> allValues = cachedMap.readAllValues();
        Set<Entry<String, Integer>> allEntries = cachedMap.readAllEntrySet();
        
        // use fast* methods when previous value is not required
        boolean isNewKey = cachedMap.fastPut("a", 100);
        boolean isNewKeyPut = cachedMap.fastPutIfAbsent("d", 33);
        long removedAmount = cachedMap.fastRemove("b");
        
        redisson.shutdown();
    }
    
}
