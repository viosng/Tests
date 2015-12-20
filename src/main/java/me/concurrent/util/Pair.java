package me.concurrent.util;

/**
 * Created by viosn_000 on 18.12.2015.
 */
public class Pair<K extends Comparable<K>, V> implements Comparable<Pair<K, V>>{
    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return key != null ? key.equals(pair.key) : pair.key == null
                && (value != null ? value.equals(pair.value) : pair.value == null);

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }


    @Override
    public int compareTo(Pair<K, V> o) {
        return this.getKey().compareTo(o.getKey());
    }
}
