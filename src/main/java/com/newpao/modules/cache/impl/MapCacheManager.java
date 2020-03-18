package com.newpao.modules.cache.impl;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.newpao.modules.cache.ICacheProvider;
import org.springframework.stereotype.Service;

/**
 * LRU (least recently used)最近最久未使用缓存。
 * 根据使用时间来判定对象是否被持续缓存，当对象被访问时放入缓存，
 * 当缓存满了，最久未被使用的对象将被移除。此缓存基于LinkedHashMap，
 * 因此当被缓存的对象每被访问一次，这个对象的key就到链表头部。这个算法简单并且非常快，
 * 他比FIFO有一个显著优势是经常使用的对象不太可能被移除缓存。
 * 缺点是当缓存满时，不能被很快的访问。
 *
 * @author admin
 */
@Service
public class MapCacheManager<K, V> implements ICacheProvider<K, V> {

    private Cache<K, V> lruCache = CacheUtil.newLRUCache(1000);

    @Override
    public void put(K key, V value) {
        lruCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return lruCache.get(key);
    }

    @Override
    public void remove(K key) {
        lruCache.remove(key);
    }

    @Override
    public void clear() {
        lruCache.clear();
    }
}
