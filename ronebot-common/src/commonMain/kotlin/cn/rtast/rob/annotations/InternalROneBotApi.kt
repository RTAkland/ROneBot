/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/27
 */

package cn.rtast.rob.annotations

@RequiresOptIn(
    message = "此API为内部API请勿直接调用",
    level = RequiresOptIn.Level.WARNING
)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
public annotation class InternalROneBotApi