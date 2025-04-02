/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.onebot11.okio

import cn.rtast.rob.event.raw.file.RawFileEvent
import okio.Path
import okio.Path.Companion.toPath
import kotlinx.io.files.Path as KotlinXIOPath

/**
 * 使用[okio.Path]来保存文件
 */
public suspend fun RawFileEvent.saveTo(path: Path): KotlinXIOPath {
    return saveTo(KotlinXIOPath(path.toString()))
}

/**
 * 异步的使用[okio.Path]来保存文件
 */
public suspend fun RawFileEvent.saveToAsync(path: Path) {
    saveToAsync(KotlinXIOPath(path.toString()))
}

/**
 * 将[kotlinx.io.files.Path]转换为[okio.Path]
 */
public fun KotlinXIOPath.toOkioPath(): Path {
    return this.toString().toPath()
}

/**
 * 将[okio.Path]转换为[kotlinx.io.files.Path]
 */
public fun Path.toKotlinIOPath(): KotlinXIOPath {
    return KotlinXIOPath(this.toString())
}