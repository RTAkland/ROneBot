/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.out.lagrange

import com.google.gson.annotations.SerializedName

data class UploadPrivateFileOut(
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