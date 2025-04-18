/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 20:01
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.ws

import io.ktor.client.engine.*
import io.ktor.client.engine.winhttp.*

public actual val nativeEngine: HttpClientEngineFactory<*> = WinHttp