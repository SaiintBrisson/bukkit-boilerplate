package me.saiintbrisson.minecraft.boilerplate.common;

import me.saiintbrisson.minecraft.boilerplate.CacheStore;
import me.saiintbrisson.minecraft.boilerplate.Dao;

public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    protected abstract Dao<K, V> getBackingInMemoryDao();

    protected abstract Dao<K, V> getBackingDataSourceDao();

    @Override
    public V getById(K key) {
        return getBackingInMemoryDao().getById(key);
    }

    @Override
    public V getOrFetchById(K key, boolean save) {
        V byId = getBackingInMemoryDao().getById(key);

        if(byId == null) {
            byId = getBackingDataSourceDao().getById(key);

            if(byId != null && save) {
                put(key, byId, false);
            }
        }

        return byId;
    }

    @Override
    public void put(K key, V value, boolean save) {
        getBackingInMemoryDao().save(key, value);

        if(save) {
            getBackingDataSourceDao().save(key, value);
        }
    }

    @Override
    public void update(K key, V value) {
        getBackingInMemoryDao().update(key, value);
        getBackingDataSourceDao().update(key, value);
    }

    @Override
    public void remove(K key, boolean delete) {
        getBackingInMemoryDao().delete(key);

        if(delete) {
            getBackingDataSourceDao().delete(key);
        }
    }

}
