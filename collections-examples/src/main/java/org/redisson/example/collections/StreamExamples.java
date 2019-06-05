package org.redisson.example.collections;

import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.PendingResult;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;

public class StreamExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RStream<String, String> stream = redisson.getStream("test");
        stream.createGroup("testGroup");
        
        StreamMessageId id1 = stream.add("1", "1");
        StreamMessageId id2 = stream.add("2", "2");
        
        // contains 2 elements
        Map<StreamMessageId, Map<String, String>> map1 = stream.readGroup("testGroup", "consumer1");

        // ack messages
        stream.ack("testGroup", id1, id2);
        
        StreamMessageId id3 = stream.add("3", "3");
        StreamMessageId id4 = stream.add("4", "4");
        
        // contains next 2 elements
        Map<StreamMessageId, Map<String, String>> map2 = stream.readGroup("testGroup", "consumer2");

        PendingResult pi = stream.listPending("testGroup");
        
        redisson.shutdown();
    }
    
}
