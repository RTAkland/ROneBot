/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/15 16:35
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session.v2

import cn.rtast.rob.entity.ISender
import kotlin.uuid.ExperimentalUuidApi

@ConsistentCopyVisibility
public data class SessionId internal constructor(val creator: ISender)

public fun ISender.toSessionId(): SessionId = SessionId(this)