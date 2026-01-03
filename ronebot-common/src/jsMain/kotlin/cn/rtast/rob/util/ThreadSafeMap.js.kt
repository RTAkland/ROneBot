/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 00:45
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util

public actual class ThreadSafeMap<K, V> actual constructor() {
    private val map = mutableMapOf<K, V>()
    public actual operator fun set(key: K, value: V) {
        map[key] = value
    }

    public actual fun put(key: K, value: V) {
        map[key] = value
    }

    public actual fun get(key: K): V? {
        return map[key]
    }

    public actual fun remove(key: K): V? {
        return map.remove(key)
    }

    public actual fun containsKey(key: K): Boolean {
        return map.containsKey(key)
    }
}