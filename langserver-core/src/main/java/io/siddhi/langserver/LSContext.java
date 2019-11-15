package io.siddhi.langserver;

/**
 * Siddhi Language server context.
 */
public interface LSContext {

    /**
     * Add new Context property.
     * @param key   Property Key
     * @param value Property value
     * @param <V>   Key Type
     */
    <V> void put(Key<V> key, V value);

    /**
     * Get property by Key.
     * @param key   Property Key
     * @param <V>   Key Type
     * @return {@link Object}   Property
     */
    <V> V get(Key<V> key);

    /**
     * @param <K> Property Key
     * @since 0.95.5
     */
    class Key<K> {
    }
}
