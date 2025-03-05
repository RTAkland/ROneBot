/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.lagrange

import com.google.gson.annotations.SerializedName

public data class ExtFile(
    /**
     * 群号
     */
    @SerializedName("group_id")
    val groupId: Long,
    /**
     * 文件ID
     */
    @SerializedName("file_id")
    val fileId: String,
    /**
     * 文件名
     */
    @SerializedName("file_name")
    val filename: String,
    /**
     * 不知道干啥的
     */
    val busid: Int,
    /**
     * 文件大小
     */
    @SerializedName("file_size")
    val fileSize: Int,
    /**
     * 上传时间
     */
    @SerializedName("upload_time")
    val uploadTime: Long,
    /**
     * 过期时间
     */
    @SerializedName("dead_time")
    val deadTime: Long,
    /**
     * 文件修改后的时间戳
     */
    @SerializedName("modify_time")
    val modifyTime: Long,
    /**
     * 上传者时间戳
     */
    @SerializedName("download_times")
    val downloadTimes: Long,
    /**
     * 上传者QQ号
     */
    @SerializedName("uploader")
    val uploader: Long,
    /**
     * 上传者名称
     */
    @SerializedName("uploader_name")
    val uploaderName: String,
)