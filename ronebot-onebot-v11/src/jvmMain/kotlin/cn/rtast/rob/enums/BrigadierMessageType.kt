/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 20:20
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.enums

import cn.rtast.jvmonly.linter.JvmOnly

@JvmOnly
public enum class BrigadierMessageType {
    Group, Private;

    public companion object {
        public fun fromMessageType(type: MessageType): BrigadierMessageType {
            return when (type) {
                InboundMessageType.private -> Private
                InboundMessageType.group -> Group
            }
        }
    }
}