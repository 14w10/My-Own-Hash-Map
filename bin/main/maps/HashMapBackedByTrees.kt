package maps

class HashMapBackedByTrees<K, V>(
    keyComparator: Comparator<K>
) : GenericHashMap<K, V>(
    bucketFactory = { TreeBasedMap(keyComparator) }
)
