/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:33 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawGroupFileUploadEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 群文件上传
 */
public data class GroupFileUploadEvent(
    override val action: MilkyAction,
    val event: RawGroupFileUploadEvent.GroupFileUpload
) : MilkyEvent
