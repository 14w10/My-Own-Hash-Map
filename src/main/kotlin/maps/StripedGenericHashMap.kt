package maps

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

abstract class StripedGenericHashMap<K, V>(
    bucketFactory: BucketFactory<K, V>
) : GenericHashMap<K, V>(bucketFactory) {
    private val locks: Array<Lock> =
        Array(initialCapacity) { ReentrantLock() }

    override val entries: Iterable<Entry<K, V>>
        get() {
            locks.forEach { it.lock() }
            try {
                return super.entries
            } finally {
                locks.forEach { it.unlock() }
            }
        }
    override val keys: Iterable<K>
        get() {
            locks.forEach { it.lock() }
            try {
                return super.keys
            } finally {
                locks.forEach { it.unlock() }
            }
        }
    override val values: Iterable<V>
        get() {
            locks.forEach { it.lock() }
            try {
                return super.values
            } finally {
                locks.forEach { it.unlock() }
            }
        }

    override fun resize() {
        locks.forEach { it.lock() }
        try {
            if (size > buckets.size * loadFactor) {
                super.resize()
            }
        } finally {
            locks.forEach { it.unlock() }
        }
    }

    override fun get(key: K): V? {
        val lock = locks[key.hashCode() % initialCapacity]
        lock.withLock {
            return super.get(key)
        }
    }

    override fun set(key: K, value: V): V? {
        return put(key, value)
    }

    override fun put(key: K, value: V): V? {
        val lock = locks[key.hashCode() % initialCapacity]
        lock.withLock {
            val bucket = findBucket(key)
            val oldValue = bucket[key]
            bucket[key] = value
            if (oldValue == null) {
                size++
                if (size > buckets.size * loadFactor) {
                    resize()
                    return null
                }
            }
            return oldValue
        }
    }

    override fun put(entry: Entry<K, V>): V? {
        return put(entry.key, entry.value)
    }

    override fun remove(key: K): V? {
        val lock = locks[key.hashCode() % initialCapacity]
        lock.withLock {
            return super.remove(key)
        }
    }

    override fun contains(key: K): Boolean {
        val lock = locks[key.hashCode() % initialCapacity]
        lock.withLock {
            return super.contains(key)
        }
    }
}
