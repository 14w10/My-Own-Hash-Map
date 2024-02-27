package maps

class StripedHashMapBackedByLists<K, V> : StripedGenericHashMap<K, V>(
    bucketFactory = { ListBasedMap() }
)
