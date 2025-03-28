/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.ext.utils.concurrency

import java.util.concurrent.ConcurrentHashMap

public actual class ThreadSafeMap<K, V> {

    private val map = ConcurrentHashMap<K, V>()

    public actual operator fun set(key: K, value: V) {
        put(key, value)
    }

    public actual fun put(key: K, value: V) {
        map[key] = value
    }

    public actual fun get(key: K): V? = map[key]

    public actual fun remove(key: K): V? {
        return map.remove(key)
    }

    public actual fun containsKey(key: K): Boolean = map.containsKey(key)
}