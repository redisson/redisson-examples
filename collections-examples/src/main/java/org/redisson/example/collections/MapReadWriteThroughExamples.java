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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.redisson.Redisson;
import org.redisson.api.MapOptions;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapLoader;
import org.redisson.api.map.MapWriter;

public class MapReadWriteThroughExamples {

    public static void main(String[] args) throws IOException, SQLException {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "admin", "admin");
        
        MapWriter<String, String> mapWriter = new MapWriter<String, String>() {
            
            @Override
            public void writeAll(Map<String, String> map) {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO student (id, name) values (?, ?)");
                    try {
                        for (Entry<String, String> entry : map.entrySet()) {
                            preparedStatement.setString(1, entry.getKey());
                            preparedStatement.setString(2, entry.getValue());
                            preparedStatement.addBatch();
                        }
                        preparedStatement.executeBatch();
                    } finally {
                        preparedStatement.close();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            
            @Override
            public void write(String key, String value) {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO student (id, name) values (?, ?)");
                    try {
                        preparedStatement.setString(1, key);
                        preparedStatement.setString(2, value);
                        preparedStatement.executeUpdate();
                    } finally {
                        preparedStatement.close();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            
            @Override
            public void deleteAll(Collection<String> keys) {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM student where id = ?");
                    try {
                        for (String key : keys) {
                            preparedStatement.setString(1, key);
                            preparedStatement.addBatch();
                        }
                        preparedStatement.executeBatch();
                    } finally {
                        preparedStatement.close();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }

            }
            
            @Override
            public void delete(String key) {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM student where id = ?");
                    try {
                        preparedStatement.setString(1, key);
                        preparedStatement.executeUpdate();
                    } finally {
                        preparedStatement.close();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        
        MapLoader<String, String> mapLoader = new MapLoader<String, String>() {
            
            @Override
            public Iterable<String> loadAllKeys() {
                List<String> list = new ArrayList<String>();
                try {
                    Statement statement = conn.createStatement();
                    try {
                        ResultSet result = statement.executeQuery("SELECT id FROM student");
                        while (result.next()) {
                            list.add(result.getString(1));
                        }
                    } finally {
                        statement.close();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }

                return list;
            }
            
            @Override
            public String load(String key) {
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT name FROM student where id = ?");
                    try {
                        preparedStatement.setString(1, key);
                        ResultSet result = preparedStatement.executeQuery();
                        if (result.next()) {
                            return result.getString(1);
                        }
                        return null;
                    } finally {
                        preparedStatement.close();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        
        MapOptions<String, String> options = 
                MapOptions.<String, String>defaults()
                    .writer(mapWriter)
                    .loader(mapLoader);
        
        RMap<String, String> map =  redisson.getMap("myMap", options);
        map.put("1", "Willy");
        map.put("2", "Andrea");
        map.put("3", "Bob");

        String name1 = map.get("1");
        String name2 = map.get("2");
        String name3 = map.get("3");
        
        redisson.shutdown();
    }
    
}
