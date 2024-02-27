package maps

class LockedListBasedMapTest : ThreadSafeCustomMutableMapTest() {
    override fun emptyThreadSafeMapIntString(): CustomMutableMap<Int, String> = LockedListBasedMap()
}
