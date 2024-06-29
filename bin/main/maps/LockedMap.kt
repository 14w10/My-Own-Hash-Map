package maps

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

abstract class LockedMap<K, V>(
    private val targetMap: CustomMutableMap<K, V>
) : CustomMutableMap<K, V> {
    protected val lock = ReentrantLock()
    override val entries: Iterable<Entry<K, V>>
        get() {
            lock.withLock {
                return targetMap.entries
            }
        }
    override val keys: Iterable<K>
        get() {
            lock.withLock {
                return targetMap.keys
            }
        }
    override val values: Iterable<V>
        get() {
            lock.withLock {
                return targetMap.values
            }
        }
}
