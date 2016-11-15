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
import java.util.Set;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

public class SetExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RSet<String> set = redisson.getSet("mySet");
        set.add("1");
        set.add("2");
        set.add("3");
        
        set.contains("1");
        
        for (String string : set) {
            // iteration through bulk loaded values
        }
        
        boolean removedValue = set.remove("1");
        set.removeAll(Arrays.asList("1", "2", "3"));
        set.containsAll(Arrays.asList("4", "1", "0"));
        
        String randomRemovedValue = set.removeRandom();
        String randomValue = set.random();

        RSet<String> secondsSet = redisson.getSet("mySecondsSet");
        secondsSet.add("4");
        secondsSet.add("5");

        // union with "mySecondsSet" and write it
        set.union(secondsSet.getName());
        // union with "mySecondsSet" without change of set
        set.readUnion(secondsSet.getName());
        
        // diff with "mySecondsSet" and write it
        set.diff(secondsSet.getName());
        // diff with "mySecondsSet" without change of set
        set.readDiff(secondsSet.getName());
        
        // intersect with "mySecondsSet" and write it
        set.intersection(secondsSet.getName());
        // intersect with "mySecondsSet" without change of set
        set.readIntersection(secondsSet.getName());
        
        Set<String> allValues = set.readAll();
        
        redisson.shutdown();
    }
    
}
