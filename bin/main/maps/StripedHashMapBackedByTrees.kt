package maps

class StripedHashMapBackedByTrees<K, V>(
    keyComparator: Comparator<K>
) : GenericHashMap<K, V>(
    bucketFactory = { TreeBasedMap(keyComparator) }
)
