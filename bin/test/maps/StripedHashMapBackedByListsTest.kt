package maps

class StripedHashMapBackedByListsTest : ThreadSafeCustomMutableMapTest() {
    override fun emptyThreadSafeMapIntString(): CustomMutableMap<Int, String> = StripedHashMapBackedByLists()
}
