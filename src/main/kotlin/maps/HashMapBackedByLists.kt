package maps

class HashMapBackedByLists<K, V> : GenericHashMap<K, V>(
    bucketFactory = { ListBasedMap() }
)
