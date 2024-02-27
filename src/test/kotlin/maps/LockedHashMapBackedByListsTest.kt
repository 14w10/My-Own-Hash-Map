package maps

class LockedHashMapBackedByListsTest : ThreadSafeCustomMutableMapTest() {
    override fun emptyThreadSafeMapIntString(): CustomMutableMap<Int, String> = LockedHashMapBackedByLists()
}
