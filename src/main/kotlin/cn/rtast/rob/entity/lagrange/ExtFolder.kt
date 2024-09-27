/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.lagrange

import com.google.gson.annotations.SerializedName

data class ExtFolder(
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("folder_id")
    val folderId: String,
    @SerializedName("folder_name")
    val folderName: String,
    @SerializedName("create_time")
    val createTime: Long,
    @SerializedName("creator")
    val creator: Long,
    @SerializedName("creator_name")
    val creatorName: String,
    @SerializedName("total_file_count")
    val totalFileCount: Int,
)