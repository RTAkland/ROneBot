/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/10 18:24
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util.ws

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

public actual fun getClientEngine(): HttpClientEngineFactory<*> {
    return Darwin
}