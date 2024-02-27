package maps

class ListBasedMap<K, V> : CustomMutableMap<K, V> {
    override val entries: CustomLinkedList<Entry<K, V>> =
        CustomLinkedList()
    override val keys: CustomLinkedList<K>
        get() = setKeys()

    private fun setKeys(): CustomLinkedList<K> {
        val result = CustomLinkedList<K>()
        for (entry in entries) {
            result.add(entry.key)
        }
        return result
    }

    override val values: CustomLinkedList<V>
        get() = setValues()

    private fun setValues(): CustomLinkedList<V> {
        val result = CustomLinkedList<V>()
        for (entry in entries) {
            result.add(entry.value)
        }
        return result
    }

    override fun get(key: K): V? {
        var result: V? = null
        for (entry in entries) {
            if (entry.key == key) {
                result = entry.value
                break
            }
        }
        return result
    }

    override fun set(key: K, value: V): V? {
        return put(key, value)
    }

    override fun put(key: K, value: V): V? {
        val result = entries.remove { it.key == key }
        entries.add(Entry(key, value))
        return result?.value
    }

    override fun put(entry: Entry<K, V>): V? {
        return put(entry.key, entry.value)
    }

    override fun remove(key: K): V? {
        val result = entries.remove { it.key == key }
        return result?.value
    }

    override fun contains(key: K): Boolean =
        get(key) != null
}
