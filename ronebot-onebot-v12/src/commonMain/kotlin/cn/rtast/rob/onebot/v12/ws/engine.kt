/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:58
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.ws

import io.ktor.client.engine.HttpClientEngineFactory

internal expect val nativeEngine: HttpClientEngineFactory<*>
