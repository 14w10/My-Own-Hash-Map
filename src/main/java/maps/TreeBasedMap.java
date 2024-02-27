package maps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TreeBasedMap<K, V> implements CustomMutableMap<K, V> {
    private TreeMapNode<K, V> root;
    private final Comparator<K> keyComparator;

    public TreeBasedMap(Comparator<K> keyComparator) {
        this.root = null;
        this.keyComparator = keyComparator;
    }

    private class EntriesIterator implements Iterator<Entry<K, V>> {
        private final Deque<TreeMapNode<K, V>> entryDeque;

        public EntriesIterator() {
            entryDeque = new LinkedList<>();
            traverse(root);
        }

        private void traverse(TreeMapNode<K, V> node) {
            while (node != null) {
                entryDeque.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !entryDeque.isEmpty();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            TreeMapNode<K, V> currentNode = entryDeque.pop();
            traverse(currentNode.getRight());
            return new Entry<>(currentNode.getKey(), currentNode.getValue());
        }
    }

    private class EntriesIterable implements Iterable<Entry<K, V>> {
        @NotNull
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntriesIterator();
        }
    }

    @NotNull
    @Override
    public Iterable<Entry<K, V>> getEntries() {
        return new EntriesIterable();
    }

    private class KeysIterable implements Iterable<K> {
        private final Iterable<Entry<K, V>> entriesIterable;

        public KeysIterable(Iterable<Entry<K, V>> entriesIterable) {
            this.entriesIterable = entriesIterable;
        }

        @NotNull
        @Override
        public Iterator<K> iterator() {
            return new KeysIterator(entriesIterable.iterator());
        }
    }

    private class KeysIterator implements Iterator<K> {
        private final Iterator<Entry<K, V>> entriesIterator;

        public KeysIterator(Iterator<Entry<K, V>> entriesIterator) {
            this.entriesIterator = entriesIterator;
        }

        @Override
        public boolean hasNext() {
            return entriesIterator.hasNext();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate");
            }
            return entriesIterator.next().getKey();
        }
    }

    @NotNull
    @Override
    public Iterable<K> getKeys() {
        return new KeysIterable(getEntries());
    }

    private class ValuesIterable implements Iterable<V> {
        private final Iterable<Entry<K, V>> entriesIterable;

        public ValuesIterable(Iterable<Entry<K, V>> entriesIterable) {
            this.entriesIterable = entriesIterable;
        }

        @NotNull
        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator(entriesIterable.iterator());
        }
    }

    private class ValuesIterator implements Iterator<V> {
        private final Iterator<Entry<K, V>> entriesIterator;

        public ValuesIterator(Iterator<Entry<K, V>> entriesIterator) {
            this.entriesIterator = entriesIterator;
        }

        @Override
        public boolean hasNext() {
            return entriesIterator.hasNext();
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate");
            }

            return entriesIterator.next().getValue();
        }
    }

    @NotNull
    @Override
    public Iterable<V> getValues() {
        return new ValuesIterable(getEntries());
    }

    private V treeGet(K key, TreeMapNode<K, V> current) {
        if (current == null)
            return null;
        int compareResult =
            keyComparator.compare(key, current.getKey());
        if (compareResult < 0)
            return treeGet(key, current.getLeft());
        else if (compareResult > 0)
            return treeGet(key, current.getRight());
        else return current.getValue();
    }

    @Nullable
    @Override
    public V get(K key) {
        return treeGet(key, root);
    }

    @Nullable
    @Override
    public V set(K key, V value) {
        return put(key, value);
    }

    private V treePut(
        TreeMapNode<K, V> newNode,
        TreeMapNode<K, V> current,
        TreeMapNode<K, V> parent
    ) {
        int compareResult =
            keyComparator.compare(newNode.getKey(), current.getKey());
        if (compareResult < 0) {
            if (current.getLeft() == null) {
                current.setLeft(newNode);
                return null;
            } else {
                return treePut(newNode, current.getLeft(), current);
            }
        } else if (compareResult > 0) {
            if (current.getRight() == null) {
                current.setRight(newNode);
                return null;
            } else {
                return treePut(newNode, current.getRight(), current);
            }
        } else {
            newNode.setLeft(current.getLeft());
            newNode.setRight(current.getRight());
            final V result = current.getValue();
            if (parent == null) {
                root = newNode;
                return result;
            }
            int compareLeftOrRight =
                keyComparator.compare(current.getKey(), parent.getKey());
            if (compareLeftOrRight < 0) {
                parent.setLeft(newNode);
            } else if (compareLeftOrRight > 0) {
                parent.setRight(newNode);
            }
            return result;
        }
    }

    @Nullable
    @Override
    public V put(K key, V value) {
        TreeMapNode<K, V> newNode = new TreeMapNode<>(key, value);
        if (root == null) {
            root = newNode;
            return null;
        }
        return treePut(newNode, root, null);
    }

    @Nullable
    @Override
    public V put(@NotNull Entry<K, V> entry) {
        return put(entry.getKey(), entry.getValue());
    }

    private void deleteNode(
        TreeMapNode<K, V> current,
        TreeMapNode<K, V> parent
    ) {
        if (parent == null) {
            if (current.getLeft() == null) {
                root = current.getRight();
            } else if (current.getRight() == null) {
                root = current.getLeft();
            }
        } else {
            int compareLeftOrRight =
                keyComparator.compare(current.getKey(), parent.getKey());
            if (compareLeftOrRight < 0) {
                if (current.getLeft() == null) {
                    parent.setLeft(current.getRight());
                } else if (current.getRight() == null) {
                    parent.setLeft(current.getLeft());
                }
            } else if (compareLeftOrRight > 0) {
                if (current.getLeft() == null) {
                    parent.setRight(current.getRight());
                } else if (current.getRight() == null) {
                    parent.setRight(current.getLeft());
                }
            }
        }
    }

    private void replaceNode(
        TreeMapNode<K, V> current,
        TreeMapNode<K, V> parent,
        TreeMapNode<K, V> remembered
    ) {
        if (current.getLeft() == null) {
            remembered.setKey(current.getKey());
            remembered.setValue(current.getValue());
            deleteNode(current, parent);
            return;
        }
        replaceNode(current.getLeft(), current, remembered);
    }

    private V treeRemove(
        K key,
        TreeMapNode<K, V> current,
        TreeMapNode<K, V> parent
    ) {
        if (current == null)
            return null;
        int compareResult = keyComparator.compare(key, current.getKey());
        if (compareResult < 0) {
            return treeRemove(key, current.getLeft(), current);
        } else if (compareResult > 0) {
            return treeRemove(key, current.getRight(), current);
        } else {
            final V result = current.getValue();
            if (current.getLeft() == null || current.getRight() == null) {
                deleteNode(current, parent);
            } else {
                replaceNode(current.getRight(), current, current);
            }
            return result;
        }
    }

    @Nullable
    @Override
    public V remove(K key) {
        return treeRemove(key, root, null);
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }
}
