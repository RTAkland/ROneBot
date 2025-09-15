/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:54 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.components

import cn.rtast.rob.starter.common.Language
import dev.fritz2.core.RenderContext

public fun RenderContext.badge(
    type: Language,
    extraContent: RenderContext.() -> Unit
): RenderContext {
    span("mr-2 tag ${type.colorClass} is-light") {
        extraContent()
        +type.languageName
    }
    return this
}