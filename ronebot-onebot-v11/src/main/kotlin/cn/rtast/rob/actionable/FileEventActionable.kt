/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.actionable

import java.io.File

/**
 * 对文件的快速操作
 * 所有API都为阻塞式
 * 但是使用了withContext
 */
public interface FileEventActionable {

    /**
     * 保存到指定的路径
     */
    public suspend fun saveTo(path: String)

    /**
     * 保存到指定的文件
     */
    public suspend fun saveTo(file: File)

    /**
     * 读取InputStream中的内容并将其转换成ByteArray
     */
    public suspend fun readBytes(): ByteArray
}