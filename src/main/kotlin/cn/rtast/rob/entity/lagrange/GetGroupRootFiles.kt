/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.lagrange

data class GetGroupRootFiles(
    val data: Data,
) {
    data class Data(
        val files: List<ExtFile>,
        val folders: List<ExtFile>,
    )
}