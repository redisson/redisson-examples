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
package org.redisson.example.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RExecutorService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.api.mapreduce.RCollator;
import org.redisson.api.mapreduce.RCollector;
import org.redisson.api.mapreduce.RMapReduce;
import org.redisson.api.mapreduce.RMapper;
import org.redisson.api.mapreduce.RReducer;

public class MapReduceExample {

    public static class WordMapper implements RMapper<String, String, String, Integer> {

        @Override
        public void map(String key, String value, RCollector<String, Integer> collector) {
            String[] words = value.split("[^a-zA-Z]");
            for (String word : words) {
                collector.emit(word, 1);
            }
        }
        
    }
    
    public static class WordReducer implements RReducer<String, Integer> {

        @Override
        public Integer reduce(String reducedKey, Iterator<Integer> iter) {
            int sum = 0;
            while (iter.hasNext()) {
               Integer i = (Integer) iter.next();
               sum += i;
            }
            return sum;
        }
        
    }

    public static class WordCollator implements RCollator<String, Integer, Integer> {

        @Override
        public Integer collate(Map<String, Integer> resultMap) {
            int result = 0;
            for (Integer count : resultMap.values()) {
                result += count;
            }
            return result;
        }
        
    }
    
    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        redisson.getExecutorService(RExecutorService.MAPREDUCE_NAME).registerWorkers(WorkerOptions.defaults().workers(3));
        
        RMap<String, String> map = redisson.getMap("myMap");
        
        map.put("1", "Alice was beginning to get very tired"); 
        map.put("2", "of sitting by her sister on the bank and");
        map.put("3", "of having nothing to do once or twice she");
        map.put("4", "had peeped into the book her sister was reading");
        map.put("5", "but it had no pictures or conversations in it");
        map.put("6", "and what is the use of a book");
        map.put("7", "thought Alice without pictures or conversation");
        
        Map<String, Integer> result = new HashMap<>();
        result.put("to", 2);
        result.put("Alice", 2);
        result.put("get", 1);
        result.put("beginning", 1);
        result.put("sitting", 1);
        result.put("do", 1);
        result.put("by", 1);
        result.put("or", 3);
        result.put("into", 1);
        result.put("sister", 2);
        result.put("on", 1);
        result.put("a", 1);
        result.put("without", 1);
        result.put("and", 2);
        result.put("once", 1);
        result.put("twice", 1);
        result.put("she", 1);
        result.put("had", 2);
        result.put("reading", 1);
        result.put("but", 1);
        result.put("it", 2);
        result.put("no", 1);
        result.put("in", 1);
        result.put("what", 1);
        result.put("use", 1);
        result.put("thought", 1);
        result.put("conversation", 1);
        result.put("was", 2);
        result.put("very", 1);
        result.put("tired", 1);
        result.put("of", 3);
        result.put("her", 2);
        result.put("the", 3);
        result.put("bank", 1);
        result.put("having", 1);
        result.put("nothing", 1);
        result.put("peeped", 1);
        result.put("book", 2);
        result.put("pictures", 2);
        result.put("conversations", 1);
        result.put("is", 1);
        
        RMapReduce<String, String, String, Integer> mapReduce = map
                    .<String, Integer>mapReduce()
                    .mapper(new WordMapper())
                    .reducer(new WordReducer());
        
        Integer count = mapReduce.execute(new WordCollator());
        System.out.println("Count " + count);
        
        Map<String, Integer> resultMap = mapReduce.execute();
        System.out.println("Result " + resultMap);
    }

    
}
