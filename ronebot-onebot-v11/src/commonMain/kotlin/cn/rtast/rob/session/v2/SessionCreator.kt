/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 23:10
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session.v2

import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateSender

public fun GroupSender.toSessionCreator(): SessionCreator<GroupSender> = SessionCreator(this)

public fun PrivateSender.toSessionCreator(): SessionCreator<PrivateSender> = SessionCreator(this)