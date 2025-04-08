/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 09:23
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("ResourceUtil")

package cn.rtast.rob.entity

import cn.rtast.rob.annotations.JvmOnly
import java.io.File
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.nio.file.Path

/**
 * Jvm的兼容方法
 * @see File.toResource
 */
@JvmOnly
public fun createResource(file: File): Resource = file.toResource()

/**
 * Jvm的兼容方法
 * @see InputStream.toResource
 */
@JvmOnly
public fun createResource(inputStream: InputStream): Resource = inputStream.toResource()

/**
 * Jvm的兼容方法
 * @see URI.toResource
 */
@JvmOnly
public fun createResource(uri: URI): Resource = uri.toResource()

/**
 * Jvm的兼容方法
 * @see URL.toResource
 */
@JvmOnly
public fun createResource(url: URL): Resource = url.toResource()

/**
 * Jvm的兼容方法
 * @see Path.toResource
 */
@JvmOnly
public fun createResource(path: Path): Resource = path.toResource()