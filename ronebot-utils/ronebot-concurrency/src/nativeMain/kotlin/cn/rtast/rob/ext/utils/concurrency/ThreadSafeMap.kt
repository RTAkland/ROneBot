/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.ext.utils.concurrency

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

public actual class ThreadSafeMap<K, V> {

    private val map = mutableMapOf<K, V>()
    private val mutex = Mutex()

    public actual operator fun set(key: K, value: V) {
        put(key, value)
    }

    public actual fun put(key: K, value: V) {
        runBlocking {
            mutex.withLock { map[key] = value }
        }
    }

    public actual fun get(key: K): V? {
        return runBlocking {
            mutex.withLock { map[key] }
        }
    }

    public actual fun remove(key: K): V? {
        return runBlocking {
            mutex.withLock { map.remove(key) }
        }
    }

    public actual fun containsKey(key: K): Boolean {
        return runBlocking {
            mutex.withLock { map.containsKey(key) }
        }
    }
}