/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:54 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawFriendFileUploadEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 好友文件上传
 */
public data class FriendFileUploadEvent(
    override val action: MilkyAction,
    val event: RawFriendFileUploadEvent.FriendFileUpload
) : MilkyEvent