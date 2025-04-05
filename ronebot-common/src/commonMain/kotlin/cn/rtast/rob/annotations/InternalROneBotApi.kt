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
@MustBeDocumented
public annotation class InternalROneBotApi