/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 09:23
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("ResourceUtil")

package cn.rtast.rob.entity

import java.io.File
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.nio.file.Path

/**
 * Jvm的兼容方法
 * @see File.toResource
 */
public fun createResource(file: File): Resource = file.toResource()

/**
 * Jvm的兼容方法
 * @see InputStream.toResource
 */
public fun createResource(inputStream: InputStream): Resource = inputStream.toResource()

/**
 * Jvm的兼容方法
 * @see URI.toResource
 */
public fun createResource(uri: URI): Resource = uri.toResource()

/**
 * Jvm的兼容方法
 * @see URL.toResource
 */
public fun createResource(url: URL): Resource = url.toResource()

/**
 * Jvm的兼容方法
 * @see Path.toResource
 */
public fun createResource(path: Path): Resource = path.toResource()