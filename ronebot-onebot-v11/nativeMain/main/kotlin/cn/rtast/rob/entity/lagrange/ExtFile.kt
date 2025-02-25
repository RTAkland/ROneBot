/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.lagrange

import com.google.gson.annotations.SerializedName

data class ExtFile(
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("file_id")
    val fileId: String,
    @SerializedName("file_name")
    val fileName: String,
    val busid: Int,
    @SerializedName("file_size")
    val fileSize: Int,
    @SerializedName("upload_time")
    val uploadTime: Long,
    @SerializedName("dead_time")
    val deadTime: Long,
    @SerializedName("modify_time")
    val modifyTime: Long,
    @SerializedName("download_times")
    val downloadTimes: Int,
    @SerializedName("uploader")
    val uploader: Long,
    @SerializedName("uploader_name")
    val uploaderName: String,
)