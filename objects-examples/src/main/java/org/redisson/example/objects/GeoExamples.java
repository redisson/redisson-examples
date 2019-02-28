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

import java.util.List;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.GeoEntry;
import org.redisson.api.GeoPosition;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeo;
import org.redisson.api.RedissonClient;

public class GeoExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        RedissonClient redisson = Redisson.create();

        RGeo<String> geo = redisson.getGeo("myGeo");
        GeoEntry entry = new GeoEntry(13.361389, 38.115556, "Palermo");
        geo.add(entry);
        geo.add(15.087269, 37.502669, "Catania");

        Double dist = geo.dist("Palermo", "Catania", GeoUnit.METERS);

        Map<String, GeoPosition> pos = geo.pos("Palermo", "Catania");

        List<String> cities = geo.radius(15, 37, 200, GeoUnit.KILOMETERS);
        List<String> allNearCities = geo.radius("Palermo", 10, GeoUnit.KILOMETERS);
        
        Map<String, Double> citiesWithDistance = geo.radiusWithDistance(15, 37, 200, GeoUnit.KILOMETERS);
        Map<String, Double> allNearCitiesDistance = geo.radiusWithDistance("Palermo", 10, GeoUnit.KILOMETERS);

        Map<String, GeoPosition> citiesWithPosition = geo.radiusWithPosition(15, 37, 200, GeoUnit.KILOMETERS);
        Map<String, GeoPosition> allNearCitiesPosition = geo.radiusWithPosition("Palermo", 10, GeoUnit.KILOMETERS);
        
        redisson.shutdown();
    }
    
}
