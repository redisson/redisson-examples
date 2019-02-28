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

import org.redisson.Redisson;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;

public class SortedSetExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RSortedSet<String> sortedSet = redisson.getSortedSet("mySortedSet");
        sortedSet.add("1");
        sortedSet.add("2");
        sortedSet.add("3");
        
        for (String string : sortedSet) {
            // iteration through bulk loaded values
        }
        
        String firstValue = sortedSet.first();
        String lastValue = sortedSet.last();
        
        boolean removedValue = sortedSet.remove("1");
        sortedSet.removeAll(Arrays.asList("1", "2", "3"));
        sortedSet.containsAll(Arrays.asList("4", "1", "0"));
        
        redisson.shutdown();
    }
    
}
