/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 5:58 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.stream

import cn.rtast.rob.onebot.OneBotAction
import java.io.File

/**
 * 上传文件
 * @param fileRetention 文件存活时间 秒
 */
public suspend fun OneBotAction.uploadFileStream(
    file: File,
    filename: String,
    fileRetention: Long = 30 * 1000,
    chunkSize: Long = 64 * 1024,
): String = uploadFileStream(
    file.readBytes(),
    filename,
    fileRetention,
    chunkSize
)