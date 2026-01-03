/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

@file:Suppress("unused")

package cn.rtast.rob.util

public expect class ThreadSafeMap<K, V>() {
    public operator fun set(key: K, value: V)
    public fun put(key: K, value: V)
    public fun get(key: K): V?
    public fun remove(key: K): V?
    public fun containsKey(key: K): Boolean
}