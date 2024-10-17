/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.actionable

import java.io.File

/**
 * 除[readBytes]之外所有api都为异步操作
 */
interface FileEventActionable {

    /**
     * 保存到指定的路径
     */
    suspend fun saveTo(path: String)

    /**
     * 保存到指定的文件
     */
    suspend fun saveTo(file: File)

    /**
     * 读取InputStream中的内容并将其转换成ByteArray
     */
    suspend fun readBytes(): ByteArray
}