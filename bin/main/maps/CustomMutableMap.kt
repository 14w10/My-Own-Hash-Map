package maps

data class Entry<K, V>(val key: K, val value: V)

interface CustomMutableMap<K, V> {
    // Provides read access to all entries of the map
    val entries: Iterable<Entry<K, V>>

    // Provides read access to all keys of the map
    val keys: Iterable<K>

    // Provides read access to all values of the map
    val values: Iterable<V>

    // Returns the value at 'key', or null if there is no such value.
    // This operator allows array-like indexing.
    operator fun get(key: K): V?

    // Operator version of 'put' to allow array-like indexing.
    operator fun set(key: K, value: V): V?

    // Associates 'value' with 'key'. Returns the previous value associated with
    // 'key', or null if there is no such previous value.
    fun put(key: K, value: V): V?

    // Associates the value of 'entry' with the key of 'entry'. Returns the previous
    // value associated with this key, or null if there is no such previous value.
    fun put(entry: Entry<K, V>): V?

    // Removes entry with key 'key' from the map if such an entry exists, returning
    // the associated value if so. Otherwise, returns null.
    fun remove(key: K): V?

    // Returns true if and only if there is some value associated with 'key' in the map
    fun contains(key: K): Boolean
}
