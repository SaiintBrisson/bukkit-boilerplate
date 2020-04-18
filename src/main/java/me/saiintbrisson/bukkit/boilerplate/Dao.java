package me.saiintbrisson.bukkit.boilerplate;

import java.util.Collection;
import java.util.function.Supplier;

public interface Dao<K, V> {

    V getById(K key);
    Collection<V> getAll();

    default V getOrDefault(K key, Supplier<V> defaultSupplier, boolean save) {
        V byId = getById(key);
        if(byId == null) {
            byId = defaultSupplier.get();

            if(save) {
                save(key, byId);
            }
        }

        return byId;
    }

    boolean save(K key, V element);

    boolean update(K key, V element);

    boolean delete(K key);

}
