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
package org.redisson.example.objects;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class SSLExamples {

    public static void main(String[] args) throws IOException {
        Config config = new Config();

        // rediss - defines to use SSL for Redis connection
        config.useSingleServer().setAddress("rediss://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        RMap<String, String> map = redisson.getMap("test");
        map.put("mykey", "myvalue");
        String value =  map.get("mykey");
        
        redisson.shutdown();
    }
    
}
