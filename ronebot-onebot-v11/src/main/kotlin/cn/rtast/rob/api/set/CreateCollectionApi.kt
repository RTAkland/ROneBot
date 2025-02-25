/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.api.set

internal data class CreateCollectionApi(
    val params: Params,
    val action: String = "create_collection",
) {
    data class Params(
        val brief: String,
        val rawData: String
    )
}