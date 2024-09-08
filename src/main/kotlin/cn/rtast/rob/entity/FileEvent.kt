/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/8
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class FileEvent(
    @SerializedName("group_id")
    val groupId: Long?,
    @SerializedName("user_id")
    val userId: Long,
    val file: File
) {
    data class File(
        val id: String,
        val name: String,
        val size: Int,
        val url: String
    )
}