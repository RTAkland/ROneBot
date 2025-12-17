/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 17:08
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.ext.utils.concurrency

public actual class ThreadSafeMap<K, V> actual constructor() {
    /**
     * js are single thread model
     */
    private val _map = mutableMapOf<K, V>()
    public actual operator fun set(key: K, value: V) {
        _map[key] = value
    }

    public actual fun put(key: K, value: V) {
        set(key, value)
    }

    public actual fun get(key: K): V? = _map[key]

    public actual fun remove(key: K): V? = _map.remove(key)

    public actual fun containsKey(key: K): Boolean = _map.containsKey(key)
}