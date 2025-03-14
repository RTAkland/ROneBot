/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.common.http.SendActionExt
import cn.rtast.rob.qqbot.enums.internal.HTTPMethod

public interface SendActionMoreExt : SendActionExt {
    public suspend fun send(method: HTTPMethod, api: String, payload: Any?): String
}