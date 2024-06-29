# My Own Map
- ListBasedMap, which represented a map as a linked list of key-value pairs;
- HashMapBackedByLists, which represented a map as an array of buckets, where each bucket was a ListBasedMap, and where hashing was used to associate a key with one of the buckets.
- TreeBasedMap, which represented a map by a binary search tree.
- Making list-based maps and hashmaps thread-safe.