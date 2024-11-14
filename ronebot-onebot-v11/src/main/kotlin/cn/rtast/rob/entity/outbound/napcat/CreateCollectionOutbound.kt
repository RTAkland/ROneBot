/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.entity.outbound.napcat

internal data class CreateCollectionOutbound(
    val params: Params,
    val action: String = "create_collection",
) {
    data class Params(
        val brief: String,
        val rawData: String
    )
}