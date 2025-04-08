/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

@file:JvmName("ResourceJvm")

package cn.rtast.rob.entity

import cn.rtast.rob.util.encodeToBase64
import java.io.File
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.readBytes


/**
 * 将一个文件转换为[Resource]对象
 */
@JvmSynthetic
public fun File.toResource(): Resource = Resource(this.readBytes().encodeToBase64())

/**
 * 将一个输入流转换为[Resource]对象
 */
@JvmSynthetic
public fun InputStream.toResource(): Resource = Resource(this.readBytes().encodeToBase64())

/**
 * 将一个URI对象转换为[Resource]对象
 */
@JvmSynthetic
public fun URI.toResource(): Resource = Resource(this.toURL().readBytes().encodeToBase64())

/**
 * 将一个URL对象转换为[Resource]对象
 */
@JvmSynthetic
public fun URL.toResource(): Resource = Resource(this.readBytes().encodeToBase64())

/**
 * 将一个Path对象转换为[Resource]对象
 */
@JvmSynthetic
public fun Path.toResource(): Resource = Resource(this.readBytes().encodeToBase64())