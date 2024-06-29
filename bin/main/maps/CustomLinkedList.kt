package maps

class CustomLinkedList<T> : MutableIterable<T> {
    var size = 0
        private set

    data class Node<T>(var value: T, var next: Node<T>? = null)

    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    fun isEmpty(): Boolean =
        size == 0

    fun peek() =
        head?.value

    fun add(value: T) {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
    }

    fun remove(): T? {
        if (!isEmpty()) size--
        val result = head?.value
        head = head?.next
        if (isEmpty()) {
            tail = null
        }
        return result
    }

    fun remove(
        predicate: (T) -> Boolean
    ): T? {
        val iterator = iterator()
        var result: T? = null
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (predicate(entry)) {
                result = entry
                iterator.remove()
                break
            }
        }
        return result
    }

    private fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    private fun removeAfter(node: Node<T>): T? {
        val result = node.next?.value
        if (node.next == tail) {
            tail = node
        }
        if (node.next != null) {
            size--
        }
        node.next = node.next?.next
        return result
    }

    override fun iterator(): MutableIterator<T> =
        object : MutableIterator<T> {
            private var index = 0
            private var lastNode: Node<T>? = null

            override fun hasNext(): Boolean {
                return index < size
            }

            override fun next(): T {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                lastNode = if (index == 0) {
                    nodeAt(0)
                } else {
                    lastNode?.next
                }
                index++
                return lastNode!!.value
            }

            override fun remove() {
                if (index == 1) {
                    this@CustomLinkedList.remove()
                } else {
                    val prevNode: Node<T> =
                        nodeAt(index - 2) ?: throw UnsupportedOperationException()
                    removeAfter(prevNode)
                    lastNode = prevNode
                }
                index--
            }
        }
}
