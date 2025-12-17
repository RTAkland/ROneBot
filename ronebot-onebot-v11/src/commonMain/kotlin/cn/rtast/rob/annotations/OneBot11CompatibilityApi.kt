/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.annotations

@RequiresOptIn(message = "该API为OneBot11兼容API可能不适用于本框架, 但是仍然有大概率可用")
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
public annotation class OneBot11CompatibilityApi