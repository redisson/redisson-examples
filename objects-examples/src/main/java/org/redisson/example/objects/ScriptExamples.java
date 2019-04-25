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

import java.util.Collections;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

public class ScriptExamples {

    public static void main(String[] args) throws InterruptedException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RBucket<String> bucket = redisson.getBucket("foo");
        bucket.set("bar");

        RScript script = redisson.getScript(StringCodec.INSTANCE);

        // execute script in read only mode
        String result = script.eval(RScript.Mode.READ_ONLY,
                               "return redis.call('get', 'foo')", 
                               RScript.ReturnType.VALUE);


        
        // execute the same script stored in Redis lua script cache

        // load lua script into Redis cache to all redis master instances
        String sha1 = script.scriptLoad("return redis.call('get', 'foo')");

        // call lua script by sha digest
        result = redisson.getScript().evalSha(RScript.Mode.READ_ONLY,
                                    sha1, RScript.ReturnType.VALUE, Collections.emptyList());
        
        
        redisson.shutdown();
    }
    
}
