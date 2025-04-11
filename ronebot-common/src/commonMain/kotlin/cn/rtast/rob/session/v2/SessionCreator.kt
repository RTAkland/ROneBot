/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 22:41
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session.v2

import cn.rtast.rob.entity.ISender

public data class SessionCreator<T : ISender>(
    val sender: T,
)