/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:08
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.annotations

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD
)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "这个API是JS平台专用的API, 请勿在非Js平台使用",
    level = RequiresOptIn.Level.WARNING
)
public annotation class JsPlatformOnly