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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

public class ListMultimapExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RListMultimap<String, Integer> multimap = redisson.getListMultimap("myMultimap");
        multimap.put("1", 1);
        multimap.put("1", 2);
        multimap.put("1", 3);
        multimap.put("2", 5);
        multimap.put("2", 6);
        multimap.put("4", 7);
        
        RList<Integer> values1 = multimap.get("1");
        RList<Integer> values2 = multimap.get("2");
        
        boolean hasEntry = multimap.containsEntry("1", 3);
        Collection<Entry<String, Integer>> entries = multimap.entries();
        Collection<Integer> values = multimap.values();
        
        boolean isRemoved = multimap.remove("1", 3);
        List<Integer> removedValues = multimap.removeAll("1");
        
        Collection<? extends Integer> newValues = Arrays.asList(5, 6, 7, 8, 9);
        boolean isNewKey = multimap.putAll("5", newValues);
        
        List<Integer> oldValues = multimap.replaceValues("2", newValues);
        List<Integer> allValues = multimap.getAll("2");
        
        long keysRemoved = multimap.fastRemove("2", "32");
        
        redisson.shutdown();
    }
    
}
