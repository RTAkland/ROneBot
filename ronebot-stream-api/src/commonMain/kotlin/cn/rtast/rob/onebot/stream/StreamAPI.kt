/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 3:26 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(InternalROneBotApi::class, ExperimentalUuidApi::class)

package cn.rtast.rob.onebot.stream

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.stream.data.CompleteStreamUploadAPI
import cn.rtast.rob.onebot.stream.data.DownloadFileStreamAPI
import cn.rtast.rob.onebot.stream.data.TestDownloadStreamAPI
import cn.rtast.rob.onebot.stream.data.UploadFileStreamAPI
import cn.rtast.rob.onebot.stream.event.StreamEvent
import cn.rtast.rob.onebot.stream.event.UploadStreamFile
import cn.rtast.rob.onebot.stream.util.chunkedBySize
import cn.rtast.rob.onebot.stream.util.collectAndCheck
import cn.rtast.rob.onebot.stream.util.collectMessages
import cn.rtast.rob.util.toJson
import kotlinx.coroutines.channels.Channel
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlin.io.encoding.Base64
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 清理临时文件
 * 对接Napcat
 */
public suspend fun OneBotAction.cleanStreamTempFile() {
    TODO()
}

/**
 * 测试下载流
 * @param collect 是否将结果`收集`
 */
public suspend fun OneBotAction.testDownloadStream(collect: Boolean = false): List<StreamEvent> {
    val channel = Channel<String>()
    val uuid = Uuid.random()
    this.createChannel(uuid, channel)
    this.send(TestDownloadStreamAPI(echo = uuid).toJson())
    return if (collect) channel.collectAndCheck<StreamEvent>() else emptyList()
}

/**
 * 文件下载流
 */
public suspend fun OneBotAction.downloadFileStream(
    file: String,
    fileId: String? = null,
    chunkSize: Long = 64 * 1024,
) {
    val channel = Channel<String>()
    val uuid = Uuid.random()
    this.createChannel(uuid, channel)
    this.send(DownloadFileStreamAPI(params = DownloadFileStreamAPI.Params(file, fileId, chunkSize), echo = uuid).toJson())
    val result = channel.collectMessages()
    println(result)
}

/**
 * 上传文件
 * @param fileRetention 文件存活时间 秒
 */
public suspend fun OneBotAction.uploadFileStream(
    buffer: Buffer,
    filename: String,
    fileRetention: Long = 30 * 1000,
    chunkSize: Long = 64 * 1024,
): String {
    val uuid = Uuid.random()
    val chunks = buffer.chunkedBySize(chunkSize)
    val channel = Channel<String>()
    this.createChannel(uuid, channel)
    chunks.chunks.forEachIndexed { index, bytes ->
        val chunkData = Base64.encode(bytes)
        val body = UploadFileStreamAPI(
            params = UploadFileStreamAPI.Params(
                uuid, chunkData, index,
                chunks.totalChunks, chunks.totalSize,
                chunks.sha256, filename, fileRetention
            ),
            echo = uuid
        ).toJson()
        this.send(body)
    }
    val completeBody = CompleteStreamUploadAPI(params = CompleteStreamUploadAPI.Params(uuid), echo = uuid).toJson()
    this.send(completeBody)
    return channel.collectAndCheck<UploadStreamFile>()
        .find { it.data.filePath != null }!!.data.filePath!!

}

/**
 * 上传文件
 * @param fileRetention 文件存活时间 秒
 */
public suspend fun OneBotAction.uploadFileStream(
    bytes: ByteArray,
    filename: String,
    fileRetention: Long = 30 * 1000,
    chunkSize: Long = 64 * 1024,
): String = uploadFileStream(Buffer().apply { write(bytes) }, filename, fileRetention, chunkSize)

/**
 * 上传文件
 * @param fileRetention 文件存活时间 秒
 */
public suspend fun OneBotAction.uploadFileStream(
    path: Path,
    filename: String,
    fileRetention: Long = 30 * 1000,
    chunkSize: Long = 64 * 1024,
): String = uploadFileStream(
    SystemFileSystem.source(path).buffered().use { it.readByteArray() },
    filename,
    fileRetention,
    chunkSize
)