/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/4
 */

package cn.rtast.rob.annotations

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER,
)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "此API为实验性API, 请谨慎使用",
    level = RequiresOptIn.Level.WARNING
)
public annotation class ExperimentalROneBotApi