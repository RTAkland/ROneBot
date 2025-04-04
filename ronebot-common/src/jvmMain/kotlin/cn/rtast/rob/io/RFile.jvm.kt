/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/4
 */

package cn.rtast.rob.io

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import kotlinx.io.files.Path
import java.io.File

@ExperimentalROneBotApi
public actual class RFile {

    private val file: File

    /**
     * 从路径构造一个[RFile]对象
     */
    public actual constructor(path: String) {
        file = File(path)
    }

    /**
     * 是否存在
     */
    public actual fun exists(): Boolean {
        return file.exists()
    }

    /**
     * 删除文件
     */
    public actual fun delete(): Boolean {
        return file.delete()
    }

    /**
     * 创建空白文件
     */
    public actual fun createNewFile() {
        file.createNewFile()
    }

    /**
     * 创建文件夹
     */
    public actual fun mkdirs(): Boolean {
        return file.mkdirs()
    }

    /**
     * 读取文本
     */
    public actual fun readText(): String {
        return file.readText(Charsets.UTF_8)
    }

    /**
     * 写入文本
     */
    public actual fun writeText(text: String) {
        file.writeText(text)
    }

    /**
     * 读取ByteArray
     */
    public actual fun readBytes(): ByteArray {
        return file.readBytes()
    }

    /**
     * 写入ByteArray
     */
    public actual fun writeBytes(bytes: ByteArray) {
        file.writeBytes(bytes)
    }

    /**
     * 将[RFile]转换为[java.io.File]
     */
    public fun toFile(): File = file

    /**
     * 将[RFile]转换为[kotlinx.io.files.Path]
     */
    public actual fun toPath(): Path = Path(file.absolutePath)

    /**
     * 转换为路径字符串
     */
    actual override fun toString(): String {
        return file.path
    }
}