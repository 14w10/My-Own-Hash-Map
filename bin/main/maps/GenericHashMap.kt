package maps

typealias BucketFactory<K, V> = () -> CustomMutableMap<K, V>

abstract class GenericHashMap<K, V>(
    private val bucketFactory: BucketFactory<K, V>,
    protected val loadFactor: Double = 0.75,
    protected val initialCapacity: Int = 16
) : CustomMutableMap<K, V> {

    protected var buckets: Array<CustomMutableMap<K, V>> =
        Array(initialCapacity) { bucketFactory() }
    protected var size: Int = 0

    protected fun findBucket(key: K): CustomMutableMap<K, V> {
        val hashCode = key.hashCode()
        val bucketIndex = hashCode % buckets.size
        return buckets[bucketIndex]
    }

    protected open fun resize() {
        val newCapacity = buckets.size * 2
        val newBuckets = Array(newCapacity) { bucketFactory() }
        for (bucket in buckets) {
            for (entry in bucket.entries) {
                val newBucketIndex = entry.key.hashCode() % newBuckets.size
                newBuckets[newBucketIndex].put(entry)
            }
        }
        buckets = newBuckets
    }

    override val entries: Iterable<Entry<K, V>>
        get() = buckets.flatMap { it.entries }

    override val keys: Iterable<K>
        get() = entries.map { it.key }

    override val values: Iterable<V>
        get() = entries.map { it.value }

    override fun get(key: K): V? {
        return findBucket(key)[key]
    }

    override fun set(key: K, value: V): V? {
        return put(key, value)
    }

    override fun put(key: K, value: V): V? {
        val bucket = findBucket(key)
        val oldValue = bucket[key]
        if (oldValue == null) {
            size++
            if (size > buckets.size * loadFactor) {
                bucket[key] = value
                resize()
                return null
            }
        }
        bucket[key] = value
        return oldValue
    }

    override fun put(entry: Entry<K, V>): V? {
        return put(entry.key, entry.value)
    }

    override fun remove(key: K): V? {
        val bucket = findBucket(key)
        val oldValue = bucket.remove(key)
        if (oldValue != null) {
            size--
        }
        return oldValue
    }

    override fun contains(key: K): Boolean {
        return findBucket(key).contains(key)
    }
}
