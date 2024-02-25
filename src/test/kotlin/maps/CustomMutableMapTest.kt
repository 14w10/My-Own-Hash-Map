package maps

/*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

abstract class CustomMutableMapTest {
    abstract fun emptyCustomMutableMapStringInt(): CustomMutableMap<String, Int>

    abstract fun emptyCustomMutableMapCollidingStringInt(): CustomMutableMap<CollidingString, Int>

    @Test
    fun `test contains when empty`() {
        val map = emptyCustomMutableMapStringInt()
        assertFalse(map.contains("Hello"))
    }

    @Test
    fun `test contains after put`() {
        val map = emptyCustomMutableMapStringInt()
        map.put("Hello", 3)
        assertTrue(map.contains("Hello"))
    }

    @Test
    fun `test get returns null`() {
        val map = emptyCustomMutableMapStringInt()
        map.put("Hello", 3)
        map.put("World", 4)
        assertNull(map.get("You"))
    }

    @Test
    fun `test get returns latest value`() {
        val map = emptyCustomMutableMapStringInt()
        map.put("Hello", 3)
        map.put("World", 4)
        map.put("Hello", 10)
        map.put("Hello", 11)
        assertEquals(11, map.get("Hello"))
    }

    @Test
    fun `test entries initially empty`() {
        val map = emptyCustomMutableMapStringInt()
        for (e in map.entries) {
            fail("Map entries should be empty")
        }
    }

    @Test
    fun `test entries after some putting`() {
        val map = emptyCustomMutableMapStringInt()
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }
        entries.forEach(map::put)
        assertEquals(entries, map.entries.sortedBy { it.value })
    }

    @Test
    fun `test entries after some setting`() {
        val map = emptyCustomMutableMapStringInt()
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }
        entries.forEach {
            map.set(it.key, it.value)
        }
        assertEquals(entries, map.entries.sortedBy { it.value })
    }

    @Test
    fun `test entries after some putting, removing and setting`() {
        val map = createMapByPuttingRemovingAndSetting()
        val entries = createExpectedEntriesFromPuttingRemovingAndSetting()
        assertEquals(entries, map.entries.sortedBy { it.value })
    }

    @Test
    fun `test values initially empty`() {
        val map = emptyCustomMutableMapStringInt()
        for (v in map.values) {
            fail("Map values should be empty")
        }
    }

    @Test
    fun `test values after some putting`() {
        val map = emptyCustomMutableMapStringInt()
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }
        entries.forEach(map::put)
        val values = entries.map { it.value }
        assertEquals(values, map.values.sorted())
    }

    @Test
    fun `test values after some setting`() {
        val map = emptyCustomMutableMapStringInt()
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }
        entries.forEach {
            map.set(it.key, it.value)
        }
        val values = entries.map { it.value }
        assertEquals(values, map.values.sorted())
    }

    @Test
    fun `test values after some putting, removing and setting`() {
        val map = createMapByPuttingRemovingAndSetting()
        val entries = createExpectedEntriesFromPuttingRemovingAndSetting()
        val values = entries.map { it.value }
        assertEquals(values, map.values.sorted())
    }

    @Test
    fun `test keys initially empty`() {
        val map = emptyCustomMutableMapStringInt()
        for (k in map.keys) {
            fail("Map keys should be empty")
        }
    }

    @Test
    fun `test keys after some putting`() {
        val map = emptyCustomMutableMapStringInt()
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }
        entries.forEach(map::put)
        val keys = entries.map { it.key }
        assertEquals(keys.sorted(), map.keys.sorted())
    }

    @Test
    fun `test keys after some setting`() {
        val map = emptyCustomMutableMapStringInt()
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }
        entries.forEach {
            map.set(it.key, it.value)
        }
        val keys = entries.map { it.key }
        assertEquals(keys.sorted(), map.keys.sorted())
    }

    @Test
    fun `test keys after some putting, removing and setting`() {
        val map = createMapByPuttingRemovingAndSetting()
        val entries = createExpectedEntriesFromPuttingRemovingAndSetting()
        val keys = entries.map { it.key }
        assertEquals(keys.sorted(), map.keys.sorted())
    }

    @Test
    fun `test entries after some putting (collision prone)`() {
        val map = emptyCustomMutableMapCollidingStringInt()
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }
        entries.forEach(map::put)
        assertEquals(entries, map.entries.sortedBy { it.value })
    }

    @Test
    fun `test entries after some setting (collision prone)`() {
        val map = emptyCustomMutableMapCollidingStringInt()
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }
        entries.forEach {
            map.set(it.key, it.value)
        }
        assertEquals(entries, map.entries.sortedBy { it.value })
    }

    @Test
    fun `test entries after some putting, removing and setting (collision prone)`() {
        val map = createCollisionProneMapByPuttingRemovingAndSetting()
        val entries = createCollisionProneExpectedEntriesFromPuttingRemovingAndSetting()
        assertEquals(entries, map.entries.sortedBy { it.value })
    }

    @Test
    fun `test values after some putting (collision prone)`() {
        val map = emptyCustomMutableMapCollidingStringInt()
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }
        entries.forEach(map::put)
        val values = entries.map { it.value }
        assertEquals(values, map.values.sorted())
    }

    @Test
    fun `test values after some setting (collision prone)`() {
        val map = emptyCustomMutableMapCollidingStringInt()
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }
        entries.forEach {
            map.set(it.key, it.value)
        }
        val values = entries.map { it.value }
        assertEquals(values, map.values.sorted())
    }

    @Test
    fun `test values after some putting, removing and setting (collision prone)`() {
        val map = createCollisionProneMapByPuttingRemovingAndSetting()
        val entries = createCollisionProneExpectedEntriesFromPuttingRemovingAndSetting()
        val values = entries.map { it.value }
        assertEquals(values, map.values.sorted())
    }

    @Test
    fun `test keys after some putting (collision prone)`() {
        val map = emptyCustomMutableMapCollidingStringInt()
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }
        entries.forEach(map::put)
        val keys = entries.map { it.key }
        assertEquals(keys.sorted(), map.keys.sorted())
    }

    @Test
    fun `test keys after some setting (collision prone)`() {
        val map = emptyCustomMutableMapCollidingStringInt()
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }
        entries.forEach {
            map.set(it.key, it.value)
        }
        val keys = entries.map { it.key }
        assertEquals(keys.sorted(), map.keys.sorted())
    }

    @Test
    fun `test keys after some putting, removing and setting (collision prone)`() {
        val map = createCollisionProneMapByPuttingRemovingAndSetting()
        val entries = createCollisionProneExpectedEntriesFromPuttingRemovingAndSetting()
        val keys = entries.map { it.key }
        assertEquals(keys.sorted(), map.keys.sorted())
    }

    class CollidingString(val string: String) : Comparable<CollidingString> {
        override fun hashCode(): Int = 5
        override fun compareTo(other: CollidingString): Int = string.compareTo(other.string)

        override fun equals(other: Any?): Boolean {
            if (other is CollidingString) {
                return string == other.string
            }
            return false
        }
    }

    private fun createMapByPuttingRemovingAndSetting(): CustomMutableMap<String, Int> {
        val map = emptyCustomMutableMapStringInt()
        for (i in 1..100) {
            assertFalse(map.contains(i.toString()))
            assertNull(map.get(i.toString()))
            assertNull(map.put(Entry(i.toString(), i)))
        }
        for (i in 1..100) {
            assertTrue(map.contains(i.toString()))
            assertEquals(i, map.get(i.toString()))
            if (i % 2 == 0) {
                val previous = map.remove(i.toString())
                assertNotNull(previous)
                assertEquals(i, previous)
            }
        }
        for (i in 1..100) {
            if (i % 4 == 0) {
                assertNull(map.get(i.toString()))
                assertFalse(map.contains(i.toString()))
                assertNull(map.set(i.toString(), i))
            }
        }
        return map
    }

    private fun createExpectedEntriesFromPuttingRemovingAndSetting(): List<Entry<String, Int>> {
        val entries = (1..100).map {
            Entry(it.toString(), it)
        }.filter {
            it.value % 2 != 0 || it.value % 4 == 0
        }
        return entries
    }

    private fun createCollisionProneMapByPuttingRemovingAndSetting(): CustomMutableMap<CollidingString, Int> {
        val map = emptyCustomMutableMapCollidingStringInt()
        for (i in 1..100) {
            assertFalse(map.contains(CollidingString(i.toString())))
            assertNull(map.get(CollidingString(i.toString())))
            assertNull(map.put(Entry(CollidingString(i.toString()), i)))
        }
        for (i in 1..100) {
            assertTrue(map.contains(CollidingString(i.toString())))
            assertEquals(i, map.get(CollidingString(i.toString())))
            if (i % 2 == 0) {
                val previous = map.remove(CollidingString(i.toString()))
                assertNotNull(previous)
                assertEquals(i, previous)
            }
        }
        for (i in 1..100) {
            if (i % 4 == 0) {
                assertNull(map.get(CollidingString(i.toString())))
                assertFalse(map.contains(CollidingString(i.toString())))
                assertNull(map.set(CollidingString(i.toString()), i))
            }
        }
        return map
    }

    private fun createCollisionProneExpectedEntriesFromPuttingRemovingAndSetting(): List<Entry<CollidingString, Int>> {
        val entries = (1..100).map {
            Entry(CollidingString(it.toString()), it)
        }.filter {
            it.value % 2 != 0 || it.value % 4 == 0
        }
        return entries
    }
}
*/
