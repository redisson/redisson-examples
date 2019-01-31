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
package org.redisson.example.objects;

import java.util.concurrent.CountDownLatch;

import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

public class TopicExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        CountDownLatch latch = new CountDownLatch(1);
        
        RTopic topic = redisson.getTopic("topic2");
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                latch.countDown();
            }
        });
        
        topic.publish("msg");
        latch.await();
        
        redisson.shutdown();
    }
    
}
