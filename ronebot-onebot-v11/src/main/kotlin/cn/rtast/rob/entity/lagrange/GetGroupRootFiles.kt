/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.lagrange

public data class GetGroupRootFiles(
    val data: RootFiles,
) {
    public data class RootFiles(
        val files: List<ExtFile>,
        val folders: List<ExtFolder>,
    )
}