/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:19
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(DelicateCoroutinesApi::class)

package cn.rtast.rob.ext.utils.concurrency

import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Js的单线程的所以不存在线程安全这一说
 * 所以直接封装了普通的map
 */
public actual class ThreadSafeMap<K, V> {

    private val map = mutableMapOf<K, V>()

    public actual operator fun set(key: K, value: V) {
        put(key, value)
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