package com.pd.api.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPair implements Serializable {

    private Long id;
    private Double weight;
    
    public DataPair() {}
    public DataPair(Long id, Double weight) {
        this.id = id;
        this.weight = weight;
    }
    
    public Long getId() {
        return id;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public static Map<Long, Double> listToMap(List<DataPair> dataPairs) {
        Map<Long, Double> map = new HashMap<Long, Double>();
        for(DataPair pair : dataPairs) {
            map.put(pair.getId(), pair.getWeight());
        }
        return map;
    }
    
    @Override
    public String toString() {
        return id + "[" + weight + "]";
    }
}
