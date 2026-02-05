/*
 * Copyright © 2026 RTAkland
 * Date: 2026/2/5 16:09
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.onebot.stream

import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.stream.uploadFileStream
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