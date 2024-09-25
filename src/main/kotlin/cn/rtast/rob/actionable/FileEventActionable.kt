/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.actionable

import java.io.File

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