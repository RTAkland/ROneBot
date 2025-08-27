/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

import kotlinx.io.files.Path

/**
 * 对文件使用快捷操作
 */
public interface FileEventActionable {
    /**
     * 保存到指定路径
     */
    public suspend fun save(path: Path)

    /**
     * 保存到指定路径
     * @param path 纯文本的路径
     */
    public suspend fun save(path: String)

    /**
     * 读取文件内容
     */
    public suspend fun readBytes(): ByteArray
}