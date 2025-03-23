/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.entity

import cn.rtast.rob.util.encodeToBase64

@ConsistentCopyVisibility
public data class Resource internal constructor(val content: String, val base64: Boolean = false)

/**
 * 将一个字节数组转换为[Resource]对象
 */
public fun ByteArray.toResource(): Resource = Resource(this.encodeToBase64())

/**
 * 将一个字符串转换为[Resource]对象
 * 这个字符串可能为一个URL或者一个base64字符串
 */
public fun String.toResource(base64: Boolean = false): Resource = Resource(this)
