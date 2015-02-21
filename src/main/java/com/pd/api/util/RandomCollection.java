package com.pd.api.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection implements Serializable{

    private final NavigableMap<Double, DataPair> map = new TreeMap<Double, DataPair>();
    private final Random random;
    private double total = 0;
    
    public RandomCollection() {this(new Random());}
    public RandomCollection(Random random) {
        this.random = random;
    }
    
    public void add(DataPair result) { add(result.getWeight(), result);}
    public void add(double weight, DataPair result) {
        if(weight <= 0) return;
        total += weight;
        map.put(total, result);
    }
    
    public DataPair next() {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
    
    public List<DataPair> nextN(int n) {
        List<DataPair> results = new ArrayList<DataPair>();
        if(n < 1) return results;
        if(n >= map.size()) {
            /* iterate values: for(K key:map.keySet())*/
            /* iterate values: for(V value:map.values())*/
            for(Map.Entry<Double, DataPair> entry : map.entrySet()) {
                results.add(entry.getValue());
            }
        } else {
            int j = 0;
            for(int i = 0; i < n * 2 || j < n; i++) {
                DataPair nextValue = next();
                if(!results.contains(nextValue)) {
                    results.add(nextValue);
                    j++;
                }
            }
        }
        return results;
    }
    
    public int size() {
        return map.size();
    }
}
