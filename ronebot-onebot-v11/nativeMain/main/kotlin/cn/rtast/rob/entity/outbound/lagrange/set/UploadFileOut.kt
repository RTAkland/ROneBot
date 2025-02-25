/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.outbound.lagrange.set

import com.google.gson.annotations.SerializedName

internal data class UploadPrivateFileOut(
    val action: String = "upload_private_file",
    val params: Params
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        val file: String,
        val name: String
    )
}

internal data class UploadGroupFileOut(
    val action: String = "upload_group_file",
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        // `local` file path
        val file: String,
        val name: String,
        val folder: String,
    )
}