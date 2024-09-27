/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.out.lagrange

import com.google.gson.annotations.SerializedName

data class UploadGroupFileOut(
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