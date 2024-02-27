package maps

class StripedHashMapBackedByTreesTest : ThreadSafeCustomMutableMapTest() {
    override fun emptyThreadSafeMapIntString(): CustomMutableMap<Int, String> =
        StripedHashMapBackedByTrees(Int::compareTo)
}
