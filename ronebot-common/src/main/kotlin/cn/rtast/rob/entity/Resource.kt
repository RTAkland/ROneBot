/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.entity

import cn.rtast.rob.util.encodeToBase64
import java.io.File
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.readBytes

@ConsistentCopyVisibility
data class Resource internal constructor(val content: String, val base64: Boolean = false)

/**
 * 将一个字节数组转换为[Resource]对象
 */
fun ByteArray.toResource(): Resource = Resource(this.encodeToBase64())

/**
 * 将一个字符串转换为[Resource]对象
 * 这个字符串可能为一个URL或者一个base64字符串
 */
fun String.toResource(base64: Boolean = false): Resource = Resource(this)

/**
 * 将一个文件转换为[Resource]对象
 */
fun File.toResource(): Resource = Resource(this.readBytes().encodeToBase64())

/**
 * 将一个输入流转换为[Resource]对象
 */
fun InputStream.toResource(): Resource = Resource(this.readBytes().encodeToBase64())

/**
 * 将一个URI对象转换为[Resource]对象
 */
fun URI.toResource(): Resource = Resource(this.toURL().readBytes().encodeToBase64())

/**
 * 将一个URL对象转换为[Resource]对象
 */
fun URL.toResource(): Resource = Resource(this.readBytes().encodeToBase64())

/**
 * 将一个Path对象转换为[Resource]对象
 */
fun Path.toResource(): Resource = Resource(this.readBytes().encodeToBase64())