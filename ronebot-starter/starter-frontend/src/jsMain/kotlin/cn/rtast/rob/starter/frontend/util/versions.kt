/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 1:04 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.util

import cn.rtast.rob.starter.frontend.entity.APIGradleVersion
import cn.rtast.rob.starter.frontend.entity.APIKotlinVersion
import cn.rtast.rob.starter.frontend.entity.APIROneBotVersion

public suspend fun getKotlinVersion(): String =
    extHttp("https://api.rtast.cn/api/kotlin").acceptJson()
        .jsonContentType().get().body()
        .fromJson<APIKotlinVersion>().version

public suspend fun getGradleVersion(): String = "9.0.0"

public suspend fun getROneBotVersion(): String =
    extHttp("https://api.rtast.cn/api/ronebot").acceptJson()
        .jsonContentType().get().body()
        .fromJson<APIROneBotVersion>().version
