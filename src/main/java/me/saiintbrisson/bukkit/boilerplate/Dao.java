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
                save(byId);
            }
        }

        return byId;
    }

    boolean save(V element);

    boolean update(V element);

    boolean delete(V element);

}
