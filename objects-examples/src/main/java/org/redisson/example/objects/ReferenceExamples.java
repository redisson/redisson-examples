package org.redisson.example.objects;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

public class ReferenceExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RMap<String, RBucket<String>> data = redisson.getMap("myMap");
        
        RBucket<String> bs = redisson.getBucket("myObject");
        bs.set("5");
        bs.set("7");
        data.put("bucket", bs);

        RBucket<String> bucket = data.get("bucket");
    }
    
}
