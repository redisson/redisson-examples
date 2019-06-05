package org.redisson.example.services;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;

public class TransactionExamples {
    
    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        RBucket<String> b = redisson.getBucket("test");
        b.set("123");
        
        RTransaction transaction = redisson.createTransaction(TransactionOptions.defaults());
        RBucket<String> bucket = transaction.getBucket("test");
        bucket.set("234");
        
        RMap<String, String> map = transaction.getMap("myMap");
        map.put("1", "2");
        
        transaction.commit();
    }

}
