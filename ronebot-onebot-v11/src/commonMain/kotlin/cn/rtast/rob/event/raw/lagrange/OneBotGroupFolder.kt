/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.event.raw.lagrange

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class OneBotGroupFolder(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 文件夹ID
     */
    @SerialName("folder_id")
    val folderId: String,
    /**
     * 文件夹名
     */
    @SerialName("folder_name")
    val folderName: String,
    /**
     * 文件夹创建时间
     */
    @SerialName("create_time")
    val createTime: Long,
    /**
     * 文件夹创建者
     */
    @SerialName("creator")
    val creator: Long,
    /**
     * 创建者名称
     */
    @SerialName("creator_name")
    val creatorName: String,
    /**
     * 文件夹内的文件数量
     */
    @SerialName("total_file_count")
    val totalFileCount: Int,
)