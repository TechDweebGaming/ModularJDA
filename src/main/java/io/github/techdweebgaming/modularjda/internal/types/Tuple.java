package io.github.techdweebgaming.modularjda.internal.types;

public class Tuple<K, V> {

    private K k;
    private V v;

    public Tuple(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getFirst() {
        return k;
    }

    public V getSecond() {
        return v;
    }


    public void setFirst(K k) {
        this.k = k;
    }

    public void setSecond(V v) {
        this.v = v;
    }

}
