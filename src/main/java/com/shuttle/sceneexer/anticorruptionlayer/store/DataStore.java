package com.shuttle.sceneexer.anticorruptionlayer.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Shuttle
 * @description: DataStore
 */
public abstract class DataStore<V> {

    private final Map<String, V> store;

    public DataStore() {
        store = new HashMap<>();
    }

    public Optional<V> get(String key) {
        return Optional.ofNullable(store.get(key));
    }

    public void put(String key, V value) {
        store.put(key, value);
    }

}
