/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.event.raw.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class OneBotGroupFile(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 文件ID
     */
    @SerialName("file_id")
    val fileId: String,
    /**
     * 文件名
     */
    @SerialName("file_name")
    val filename: String,
    /**
     * 不知道干啥的
     */
    val busid: Int,
    /**
     * 文件大小
     */
    @SerialName("file_size")
    val fileSize: Int,
    /**
     * 上传时间
     */
    @SerialName("upload_time")
    val uploadTime: Long,
    /**
     * 过期时间
     */
    @SerialName("dead_time")
    val deadTime: Long,
    /**
     * 文件修改后的时间戳
     */
    @SerialName("modify_time")
    val modifyTime: Long,
    /**
     * 上传者时间戳
     */
    @SerialName("download_times")
    val downloadTimes: Long,
    /**
     * 上传者QQ号
     */
    @SerialName("uploader")
    val uploader: Long,
    /**
     * 上传者名称
     */
    @SerialName("uploader_name")
    val uploaderName: String,
)