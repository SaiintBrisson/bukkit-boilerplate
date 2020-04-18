package me.saiintbrisson.bukkit.boilerplate;

import java.util.function.Supplier;

/**
 * Practicing some design patterns.
 *
 * This class interfaces a bridge between an in-memory cache, and a data source.
 *
 * Read more at:
 * https://docs.oracle.com/cd/E15357_01/coh.360/e15723/cache_rtwtwbra.htm#COHDG5178
 *
 * @param <K> the model identifier type.
 * @param <V> the model type.
 */
public interface CacheStore<K, V> {

    /**
     * Returns an element stored in memory.
     *
     * @param key the element identifier to be searched.
     * @return returns the element that corresponds to the provided identifier.
     */
    V getById(K key);

    /**
     * Returns an element stored in memory, if the ID isn't present in memory,
     * the controller will search for the provided id in a storage.
     *
     * @param key the element identifier to be searched.
     * @return returns the element that corresponds to the provided identifier.
     */
    default V getOrFetchById(K key) {
        return getOrFetchById(key, false);
    }

    /**
     * Returns an element stored in memory, if the ID isn't present in memory,
     * the controller will search for the provided id in a storage.
     *
     * @param key the element identifier to be searched.
     * @param save whether the element returned should be saved in memory
     * @return returns the element that corresponds to the provided identifier.
     */
    V getOrFetchById(K key, boolean save);

    /**
     * Returns an element stored in memory, if the ID isn't present in memory,
     * the returned value will be provided by the passed supplier.
     *
     * @param key the element identifier to be searched.
     * @param defaultSupplier the supplier which will provide the default value.
     * @param save whether the default value should be inserted into the in-memory cache and data-source.
     * @return the value returned from the in-memory cache.
     */
    default V getOrDefault(K key, Supplier<V> defaultSupplier, boolean save) {
        V byId = getOrFetchById(key);
        if(byId == null) {
            byId = defaultSupplier.get();

            if(save) {
                put(key, byId, true);
            }
        }

        return byId;
    }

    /**
     * Inserts an element into the in-memory cache.
     *
     * @param key the element identifier to be inserted.
     * @param value the element itself.
     */
    default void put(K key, V value) {
        put(key, value, false);
    }

    /**
     * Inserts an element into the in-memory cache,
     * and if save is true, the element will be inserted into the data-source too.
     *
     * @param key the element identifier to be inserted.
     * @param value the element itself.
     * @param save whether the element should be inserted into the data-source.
     */
    void put(K key, V value, boolean save);

    void update(K key, V value);

    default void update(K key, V value, boolean remove) {
        update(key, value);

        if(remove) {
            remove(key, false);
        }
    }

    /**
     * Removes an element from the in-memory cache.
     *
     * @param key the element identifier to be removed.
     */
    default void remove(K key) {
        remove(key, false);
    }

    /**
     * Removes an element from the in-memory cache,
     * if delete is true, the element will be deleted from the data-source.
     *
     * @param key the element identifier to be removed.
     * @param delete whether the element should be deleted from the data-source.
     */
    void remove(K key, boolean delete);

}
