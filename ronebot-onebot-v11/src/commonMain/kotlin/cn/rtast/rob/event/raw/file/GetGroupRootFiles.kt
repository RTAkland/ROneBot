/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.event.raw.file

import cn.rtast.rob.event.raw.group.OneBotGroupFile
import cn.rtast.rob.event.raw.group.OneBotGroupFolder
import kotlinx.serialization.Serializable

@Serializable
public data class GetGroupRootFiles(
    val data: RootFiles,
) {
    @Serializable
    public data class RootFiles(
        val files: List<OneBotGroupFile>,
        val folders: List<OneBotGroupFolder>,
    )
}