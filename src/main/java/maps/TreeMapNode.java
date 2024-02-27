package maps;

public class TreeMapNode<K, V> {
    private K key;
    private V value;
    private TreeMapNode<K, V> left;
    private TreeMapNode<K, V> right;

    TreeMapNode(
        K key,
        V value
    ) {
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    void setLeft(TreeMapNode<K, V> left) {
        this.left = left;
    }

    void setRight(TreeMapNode<K, V> right) {
        this.right = right;
    }

    TreeMapNode<K, V> getLeft() {
        return this.left;
    }

    TreeMapNode<K, V> getRight() {
        return this.right;
    }

    void setKey(K key) {
        this.key = key;
    }

    void setValue(V value) {
        this.value = value;
    }

    K getKey() {
        return this.key;
    }

    V getValue() {
        return this.value;
    }
}
