/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.backend.enums

public enum class ExtraFeature(
    public val featureString: String,
    public val featureName: String,
    public val replacement: String
) {
    Permission(
        "permission",
        "权限控制",
        "implementation(\"cn.rtast.rob:ronebot-permission:{{ROB_VERSION}}\")"
    ),
    StringFormat(
        "string-format",
        "字符串格式化工具",
        "implementation(\"cn.rtast.rob:ronebot-string-format:{{ROB_VERSION}}\")"
    );

    public companion object {
        public fun fromList(str: String): ExtraFeature? {
            return when (str) {
                Permission.featureString -> Permission
                StringFormat.featureString -> StringFormat
                else -> null
            }
        }
    }
}