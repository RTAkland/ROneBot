/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:06 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 群文件
 */
@Serializable
public data class GroupFile(
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
     * 父文件夹ID
     */
    @SerialName("parent_folder_id")
    val parentFolderId: String?,
    /**
     * 文件大小
     * 字节
     */
    @SerialName("file_size")
    val fileSize: Long,
    /**
     * 上传时间
     * UNIX时间戳 秒
     */
    @SerialName("uploaded_time")
    val uploadedTime: Long,
    /**
     * 过期时间
     * UNIX时间戳 秒
     */
    @SerialName("expire_time")
    val expireTime: Long,
    /**
     * 上传者QQ号
     */
    @SerialName("uploader_id")
    val uploaderId: Long,
    /**
     * 下载次数
     */
    @SerialName("downloaded_times")
    val downloadedTimes: Int
)

/**
 * 群文件夹
 */
@Serializable
public data class GroupFolder(
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
     * 父文件夹ID
     */
    @SerialName("parent_folder_id")
    val parentFolderId: String?,
    /**
     * 文件夹名
     */
    @SerialName("folder_name")
    val folderName: String,
    /**
     * 创建时间
     * UNIX时间戳 秒
     */
    @SerialName("created_time")
    val createdTime: Long,
    /**
     * 最后修改时间
     * UNIX时间戳 秒
     */
    @SerialName("last_modified_time")
    val lastModifiedTime: Long,
    /**
     * 创建者QQ号
     */
    @SerialName("creator_id")
    val creatorId: Long,
    /**
     * 文件数量
     */
    @SerialName("file_count")
    val fileCount: Int
)