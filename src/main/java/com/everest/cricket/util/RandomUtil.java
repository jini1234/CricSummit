package com.everest.cricket.util;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    private final NavigableMap<Integer, Integer> map = new TreeMap<>();
    private int total = 0;

    public void add(int key, int weight) {
        total += weight;
        map.put(total, key);
    }

    public int getOneRandom() {
        int value = ThreadLocalRandom.current().nextInt(1, total + 1);
        return map.ceilingEntry(value).getValue();
    }
}
