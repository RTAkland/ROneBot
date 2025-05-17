/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:43 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.util.http

import io.ktor.client.engine.*
import io.ktor.client.engine.curl.*

public actual val clientEngine: HttpClientEngineFactory<*> = Curl