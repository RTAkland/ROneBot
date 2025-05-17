/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.entity

import cn.rtast.rob.exceptions.NonBase64ResourceException
import cn.rtast.rob.exceptions.NonFilePathResourceException
import cn.rtast.rob.util.decodeToByteArrayBase64
import cn.rtast.rob.util.encodeToBase64
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray

@ConsistentCopyVisibility
public data class Resource internal constructor(
    val content: String,
    val base64: Boolean = false,
    val isFile: Boolean = false
) {
    override fun toString(): String {
        return if (base64) "base64://$content"
        else if (isFile) "file://$content"
        else content
    }
}

/**
 * 将一个字节数组转换为[Resource]对象
 * ByteArray表示一个资源, 可以是一张图片或者是一个文件, 但最终都会被转换为base64字符串
 */
public fun ByteArray.toResource(): Resource = Resource(this.encodeToBase64(), true, isFile = false)

/**
 * 将一个字符串转换为[Resource]对象
 * 这个字符串可能为一个URL或者一个base64字符串
 */
public fun String.toResource(base64: Boolean = false, isFile: Boolean = false): Resource =
    Resource(this, base64, isFile)

/**
 * 将Kotlinx-io的[Path]对象转换为[Resource]对象
 */
public fun Path.toResource(): Resource =
    SystemFileSystem.source(this).buffered().use { Resource(it.readByteArray().encodeToBase64(), true) }

/**
 * 将[Resource]对象转换为字节数组
 * 仅限传入的[Resource]对象是base64编码的
 */
@Throws(NonBase64ResourceException::class)
public fun Resource.toByteArray(): ByteArray =
    if (base64) content.decodeToByteArrayBase64() else throw NonBase64ResourceException("这个资源对象并不是Base64编码后的资源!")

/**
 * 安全的将[Resource]转换为[ByteArray]如果转换失败则返回null
 */
public fun Resource.toByteArraySafely(): ByteArray? {
    return try {
        this.toByteArray()
    } catch (_: NonBase64ResourceException) {
        null
    }
}

/**
 * 将[Resource]对象转换成file://
 */
public fun Resource.toFileResource(): String =
    if (isFile) "file://$content" else throw NonFilePathResourceException("这个资源对象并不是一个文件路径")