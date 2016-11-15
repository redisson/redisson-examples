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
import java.util.HashMap;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

public class ScoredSortedSetExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("mySortedSet");
        set.add(10, "1");
        set.add(20, "2");
        set.add(30, "3");
        
        for (String string : set) {
            // iteration through bulk loaded values
        }
        
        Map<String, Double> newValues = new HashMap<>();
        newValues.put("4", 40D);
        newValues.put("5", 50D);
        newValues.put("6", 60D);
        Long newValuesAmount = set.addAll(newValues);
        
        Double scoreResult = set.addScore("2", 10);
        set.contains("4");
        set.containsAll(Arrays.asList("3", "4", "5"));
        
        String firstValue = set.first();
        String lastValue = set.last();
        
        String polledFirst = set.pollFirst();
        String polledLast = set.pollLast();

        // use read method to fetch all objects
        Collection<String> allValues = set.readAll();
        
        redisson.shutdown();
    }
    
}
