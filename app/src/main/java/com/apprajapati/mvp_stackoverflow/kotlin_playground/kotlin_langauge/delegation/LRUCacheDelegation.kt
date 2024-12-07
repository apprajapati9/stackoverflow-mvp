package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge.delegation

import kotlin.reflect.KProperty

/**
Example for LRU (Least Recently Used) cache delegate ensures that frequently accessed data remains in memory, while less-used data is evicted, maintaining scalability.

Simple LRU cache implementation
@param capacity The maximum num of entries the cache can hold

 */

class LRUCache<K, V>(val capacity: Int) {

    //LinkedHashMap with accessOrder=true to maintain LRU order
    private val cache = object : LinkedHashMap<K, V>(capacity, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
            return size > capacity
        }
    }

    @Synchronized
    fun get(key: K): V? = cache[key]

    @Synchronized
    fun put(key: K, value: V) = cache.put(key, value)
}

class CachingDelegate<K, V>(
    private val cache: LRUCache<K, V>,
    private val loader: (K) -> V
) {

    operator fun getValue(ref: Any?, property: KProperty<*>): (K) -> V = { key ->
        cache.get(key) ?: synchronized(cache) {
            cache.get(key) ?: loader(key).also {
                //println("putting in... $key, exists? - ${cache[key]}")
                cache.put(key, it)
            }
        }
    }
}

class DataManager(capacity: Int) {

    private val dataCache = LRUCache<Int, List<String>>(capacity)

    val getData: (Int) -> List<String> by CachingDelegate(dataCache) { key ->
        println("Loading data...")
        List(456) {
            "Data $it for the key $key"
        }
    }

    fun processData(key: Int) {
        val data = getData(key)
        println("processing ${data.size} items for the key $key")
    }
}

fun main() {

    val dataManager = DataManager(5)

    for (key in 1..10) {
        dataManager.processData(key)
    }

    //access again to update their usage.
    dataManager.processData(1)
    dataManager.processData(2)
    dataManager.processData(3)

    for (key in 11..16) {
        dataManager.processData(key)
    }

    dataManager.processData(2) //should be cached because last accessed
    dataManager.processData(5) // may be removed
    dataManager.processData(6) // may be removed
}

/* Best practices for Property Delegation

1. Use built-in when possible like lazy, observable and map delegates.

2. Encapsulate reusable logic. If repeating getter and setter logic across multiple properties
or classes, consider creating a custom delegate to encapsulate and reuse that behavior.

3. Delegate should have a single responsibility.

4. Handle exception

5. Ensure thread safety by using synchronized or mutex to prevent race conditions.

6. Document delegate behavior and Test them

7. Delegates are ideal for handling cross cutting concerns such as logging, caching or validation without polluting business logic.

 */