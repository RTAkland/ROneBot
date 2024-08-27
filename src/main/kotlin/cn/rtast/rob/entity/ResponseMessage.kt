/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    val status: String,
    @SerializedName("retcode")
    val retCode: Int,
    val data: JsonElement
)