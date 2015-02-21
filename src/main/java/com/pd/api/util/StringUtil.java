package com.pd.api.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    public static String MAP_SEPARATOR = "&";
    public static String PAIR_SEPARATOR = ":";
    
    public static String valueMapToString(Map<Long, Double> map) {
        StringBuilder stringBuilder = new StringBuilder();
        String mapSeparator = "";
        for(Long key : map.keySet()) {
            Double value = map.get(key);
            stringBuilder.append(mapSeparator);
            stringBuilder.append(key != null ? key : "");
            stringBuilder.append(PAIR_SEPARATOR);
            stringBuilder.append(value != null ?  value : "");
            mapSeparator = MAP_SEPARATOR;
        }
        return stringBuilder.toString();
    }
    
    public static Map<Long, Double> stringToValueMap(String input) {
        Map<Long, Double> map = new HashMap<Long, Double>();
        String[]  pairs = input.split(MAP_SEPARATOR);
        for(String pair : pairs) {
            String[] dataPair = pair.split(PAIR_SEPARATOR);
            if(dataPair.length < 2) continue;
            Long id = Long.parseLong(dataPair[0]);
            Double value = Double.parseDouble(dataPair[1]);
            map.put(id, value);
        }
        return map;
    }
    
    /*public static RandomCollection<Long> stringToRandomCollection(String input) {
        RandomCollection<Long> randomcollection = new RandomCollection<Long>();
        String[] pairs = input.split(MAP_SEPARATOR);
        for(String pair : pairs) {
            String[] dataPair = pair.split(PAIR_SEPARATOR);
            if(dataPair.length < 2) continue;
            Long id = Long.parseLong(dataPair[0]);
            Double value = Double.parseDouble(dataPair[1]);
            randomcollection.add(value, id);
        }
        return randomcollection;
    }*/
}
