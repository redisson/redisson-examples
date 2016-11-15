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
import java.util.HashSet;
import java.util.Set;

import org.redisson.Redisson;
import org.redisson.api.RLexSortedSet;
import org.redisson.api.RedissonClient;

public class LexSortedSetExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RLexSortedSet set = redisson.getLexSortedSet("sortedSet");
        set.add("1");
        set.add("2");
        set.add("3");
        
        for (String string : set) {
            // iteration through bulk loaded values
        }

        Set<String> newValues = new HashSet<>();
        newValues.add("4");
        newValues.add("5");
        newValues.add("6");
        set.addAll(newValues);
        
        set.contains("4");
        set.containsAll(Arrays.asList("3", "4", "5"));
        
        String firstValue = set.first();
        String lastValue = set.last();
        
        String polledFirst = set.pollFirst();
        String polledLast = set.pollLast();
        
        redisson.shutdown();
        
        // use read method to fetch all objects
        Collection<String> allValues = set.readAll();
        
        redisson.shutdown();
    }
    
}
