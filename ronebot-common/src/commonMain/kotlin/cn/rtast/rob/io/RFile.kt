/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/4
 */

@file:Suppress("unused")

package cn.rtast.rob.io

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import kotlinx.io.files.Path

@ExperimentalROneBotApi
public expect class RFile {

    /**
     * 从路径构造一个[RFile]对象
     */
    public constructor(path: String)

    /**
     * 是否存在
     */
    public fun exists(): Boolean

    /**
     * 删除文件
     */
    public fun delete(): Boolean

    /**
     * 创建空白文件
     */
    public fun createNewFile()

    /**
     * 创建文件夹
     */
    public fun mkdirs(): Boolean

    /**
     * 读取文本
     */
    public fun readText(): String

    /**
     * 写入文本
     */
    public fun writeText(text: String)

    /**
     * 读取ByteArray
     */
    public fun readBytes(): ByteArray

    /**
     * 写入ByteArray
     */
    public fun writeBytes(bytes: ByteArray)

    /**
     * 将[RFile]转换为[kotlinx.io.files.Path]
     */
    public fun toPath(): Path

    /**
     * 转换为路径字符串
     */
    override fun toString(): String
}