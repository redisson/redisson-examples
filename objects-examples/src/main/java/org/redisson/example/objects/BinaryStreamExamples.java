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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.redisson.Redisson;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;

public class BinaryStreamExamples {

    public static void main(String[] args) throws IOException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RBinaryStream stream = redisson.getBinaryStream("myStream");
        
        byte[] values = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        stream.trySet(values);
        stream.set(values);
        
        InputStream is = stream.getInputStream();
        StringBuilder sb = new StringBuilder();
        int ch;
        while((ch = is.read()) != -1) {
            sb.append((char)ch);
        }
        String str = sb.toString();
        
        OutputStream os = stream.getOutputStream();
        for (int i = 0; i < values.length; i++) {
            byte c = values[i];
            os.write(c);
        }
        
        redisson.shutdown();
    }
    
}
