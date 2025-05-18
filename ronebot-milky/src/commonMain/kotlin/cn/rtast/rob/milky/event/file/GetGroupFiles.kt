/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 9:03 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.file

import cn.rtast.rob.milky.event.common.GroupFile
import cn.rtast.rob.milky.event.common.GroupFolder
import kotlinx.serialization.Serializable

/**
 * 获取群文件列表
 */
@Serializable
public data class GetGroupFiles(
    val data: GroupFiles
) {
    @Serializable
    public data class GroupFiles(
        /**
         * 文件列表
         */
        val files: List<GroupFile>,
        /**
         * 文件夹列表
         */
        val folder: List<GroupFolder>
    )
}