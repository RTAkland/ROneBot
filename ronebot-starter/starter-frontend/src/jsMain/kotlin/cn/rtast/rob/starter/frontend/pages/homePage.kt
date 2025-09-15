/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:51 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.pages

import cn.rtast.rob.starter.frontend.coroutineScope
import cn.rtast.rob.starter.frontend.util.getGradleVersion
import cn.rtast.rob.starter.frontend.util.getKotlinVersion
import cn.rtast.rob.starter.frontend.util.getROneBotVersion
import dev.fritz2.core.RenderContext
import kotlinx.coroutines.launch

public fun RenderContext.homePage() {
    coroutineScope.launch {
        val robVersion = getROneBotVersion()
        val gradleVersion = getGradleVersion()
        val kotlinVersion = getKotlinVersion()

    }
}