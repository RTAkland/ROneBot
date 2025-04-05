/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RequiresOptIn(
    message = "这是一个OneBot11的内部API, 请谨慎调用",
    level = RequiresOptIn.Level.WARNING
)
@MustBeDocumented
public annotation class InternalOneBot11Api