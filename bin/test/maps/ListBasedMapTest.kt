package maps

class ListBasedMapTest : CustomMutableMapTest() {
    override fun emptyCustomMutableMapStringInt(): CustomMutableMap<String, Int> = ListBasedMap()

    override fun emptyCustomMutableMapCollidingStringInt(): CustomMutableMap<CollidingString, Int> = ListBasedMap()
}
