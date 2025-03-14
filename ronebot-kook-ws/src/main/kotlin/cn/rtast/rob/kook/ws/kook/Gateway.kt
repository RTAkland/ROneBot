/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.ws.kook

import cn.rtast.rob.common.http.Http
import cn.rtast.rob.kook.common.KOOK_BASE_URL

internal fun getWebsocketGatewayURL(): String {
    return Http.get("$KOOK_BASE_URL/gateway/index")
}