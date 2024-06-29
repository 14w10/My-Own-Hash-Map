package maps

import kotlin.concurrent.withLock

class LockedListBasedMap<K, V>(
    private val listBasedMap: ListBasedMap<K, V>
) : LockedMap<K, V>(listBasedMap) {
    constructor() : this(ListBasedMap())

    override fun get(key: K): V? {
        lock.withLock {
            return listBasedMap[key]
        }
    }

    override fun set(key: K, value: V): V? {
        lock.withLock {
            return listBasedMap.set(key, value)
        }
    }

    override fun put(key: K, value: V): V? {
        lock.withLock {
            return listBasedMap.put(key, value)
        }
    }

    override fun put(entry: Entry<K, V>): V? {
        lock.withLock {
            return listBasedMap.put(entry)
        }
    }

    override fun remove(key: K): V? {
        lock.withLock {
            return listBasedMap.remove(key)
        }
    }

    override fun contains(key: K): Boolean {
        lock.withLock {
            return listBasedMap.contains(key)
        }
    }
}
