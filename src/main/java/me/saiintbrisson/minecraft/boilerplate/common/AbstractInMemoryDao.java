package me.saiintbrisson.minecraft.boilerplate.common;

import com.google.common.cache.Cache;
import me.saiintbrisson.minecraft.boilerplate.Dao;

import java.util.Collection;

public abstract class AbstractInMemoryDao<K, V> implements Dao<K, V> {

    protected abstract Cache<K, V> getBackingCache();

    @Override
    public V getById(K key) {
        return getBackingCache().getIfPresent(key);
    }

    @Override
    public Collection<V> getAll() {
        return getBackingCache().asMap().values();
    }

    @Override
    public boolean save(K key, V element) {
        getBackingCache().put(key, element);
        return false;
    }

    @Override
    public boolean update(K key, V element) {
        return save(key, element);
    }

    @Override
    public boolean delete(K key) {
        getBackingCache().invalidate(key);
        return false;
    }

}
