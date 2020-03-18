package com.newpao.modules.cache;

/**
 * ICacheProvider
 *
 * @author admin
 */
public interface ICacheProvider<K, V> {

    public void put(K key, V value);

    public V get(K key);

    public void remove(K key);

    public void clear();

}
