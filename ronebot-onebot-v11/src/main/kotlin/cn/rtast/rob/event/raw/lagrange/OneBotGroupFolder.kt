/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.event.raw.lagrange

import com.google.gson.annotations.SerializedName

public data class OneBotGroupFolder(
    /**
     * 群号
     */
    @SerializedName("group_id")
    val groupId: Long,
    /**
     * 文件夹ID
     */
    @SerializedName("folder_id")
    val folderId: String,
    /**
     * 文件夹名
     */
    @SerializedName("folder_name")
    val folderName: String,
    /**
     * 文件夹创建时间
     */
    @SerializedName("create_time")
    val createTime: Long,
    /**
     * 文件夹创建者
     */
    @SerializedName("creator")
    val creator: Long,
    /**
     * 创建者名称
     */
    @SerializedName("creator_name")
    val creatorName: String,
    /**
     * 文件夹内的文件数量
     */
    @SerializedName("total_file_count")
    val totalFileCount: Int,
)