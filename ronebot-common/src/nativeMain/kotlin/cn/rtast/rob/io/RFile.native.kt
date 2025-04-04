/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/4
 */

package cn.rtast.rob.io

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import io.ktor.utils.io.*
import kotlinx.io.*
import kotlinx.io.bytestring.ByteString
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

@ExperimentalROneBotApi
public actual class RFile {

    private val path: Path

    /**
     * 从路径构造一个[RFile]对象
     */
    public actual constructor(path: String) {
        this@RFile.path = Path(path)
    }

    /**
     * 是否存在
     */
    public actual fun exists(): Boolean {
        return SystemFileSystem.exists(path)
    }

    /**
     * 删除文件
     */
    public actual fun delete(): Boolean {
        SystemFileSystem.delete(path)
        return true
    }

    /**
     * 删除文件但是可以选择是否必须删除
     */
    public fun delete(mustDelete: Boolean): Boolean {
        SystemFileSystem.delete(path, mustDelete)
        return true
    }

    /**
     * 创建空白文件
     */
    public actual fun createNewFile() {
        val buffer = Buffer().apply { writeBytes(byteArrayOf()) }
        SystemFileSystem.sink(path).use { it.write(buffer, buffer.size) }
    }

    /**
     * 创建文件夹
     */
    public actual fun mkdirs(): Boolean {
        SystemFileSystem.createDirectories(path)
        return true
    }

    /**
     * 创建文件夹但是可以选择是否必须创建
     */
    public fun mkdirs(mustCreate: Boolean): Boolean {
        SystemFileSystem.createDirectories(path, mustCreate)
        return true
    }

    /**
     * 读取文本
     */
    public actual fun readText(): String {
        return SystemFileSystem.source(path).use { it.buffered().readText() }
    }

    /**
     * 写入文本
     */
    public actual fun writeText(text: String) {
        val buffer = Buffer().apply { writeString(text) }
        SystemFileSystem.sink(path).use { it.write(buffer, buffer.size) }
    }

    /**
     * 读取ByteArray
     */
    public actual fun readBytes(): ByteArray {
        return SystemFileSystem.source(path).use { it.buffered().readByteArray() }
    }


    /**
     * 写入ByteArray
     */
    public actual fun writeBytes(bytes: ByteArray) {
        val buffer = Buffer().apply { write(bytes) }
        SystemFileSystem.sink(path).use { it.write(buffer, buffer.size) }
    }

    /**
     * 读取ByteString
     */
    public fun readByteString(): ByteString {
        return SystemFileSystem.source(path).use { it.buffered().readByteString() }
    }

    /**
     * 读取ByteString, 但是可以读取指定长度的ByteString
     */
    public fun readByteString(byteCount: Int): ByteString {
        return SystemFileSystem.source(path).use { it.buffered().readByteString(byteCount) }
    }

    /**
     * 将[RFile]转换为[kotlinx.io.files.Path]
     */
    public actual fun toPath(): Path = path

    /**
     * 转换为路径字符串
     */
    actual override fun toString(): String {
        return path.toString()
    }
}