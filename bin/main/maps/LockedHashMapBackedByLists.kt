package maps

import kotlin.concurrent.withLock

class LockedHashMapBackedByLists<K, V>(
    private val hashMapBackedByLists: HashMapBackedByLists<K, V>
) : LockedMap<K, V>(hashMapBackedByLists) {
    constructor() : this(HashMapBackedByLists())
    override fun get(key: K): V? {
        lock.withLock {
            return hashMapBackedByLists[key]
        }
    }

    override fun set(key: K, value: V): V? {
        lock.withLock {
            return hashMapBackedByLists.set(key, value)
        }
    }

    override fun put(key: K, value: V): V? {
        lock.withLock {
            return hashMapBackedByLists.put(key, value)
        }
    }

    override fun put(entry: Entry<K, V>): V? {
        lock.withLock {
            return hashMapBackedByLists.put(entry)
        }
    }

    override fun remove(key: K): V? {
        lock.withLock {
            return hashMapBackedByLists.remove(key)
        }
    }

    override fun contains(key: K): Boolean {
        lock.withLock {
            return hashMapBackedByLists.contains(key)
        }
    }
}
