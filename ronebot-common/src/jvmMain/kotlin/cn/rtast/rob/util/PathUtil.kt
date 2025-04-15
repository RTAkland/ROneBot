/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/9 13:59
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("PathUtil")


package cn.rtast.rob.util

import kotlinx.io.files.Path

/**
 * 为Java使用者创建的Path工具函数
 * 传入字符串会返回[kotlinx.io.files.Path]对象
 * e.g.
 * ```kotlin
 * var path = PathUtil.pathOf("some/path/to/file.xtx")
 * ```
 */
public fun pathOf(path: String): Path = Path(path)

/**
 * 为Java使用者创建的工具函数
 * 传入一个已经构造好的Path对象,
 * 再分块传入若干个部分
 * e.g.
 * ```kotlin
 * Path path = ...
 * var multiPartsPath = PathUtil.pathOf(path, "some", "path", "to", "file.txt")
 * ```
 */
public fun pathOf(path: Path, vararg parts: String): Path = Path(path, *parts)