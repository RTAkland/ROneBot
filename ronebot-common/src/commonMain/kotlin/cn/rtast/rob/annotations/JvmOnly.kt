/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 18:12
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.annotations

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "这个API仅推荐在Java中使用",
    level = RequiresOptIn.Level.ERROR
)
public annotation class JvmOnly()
