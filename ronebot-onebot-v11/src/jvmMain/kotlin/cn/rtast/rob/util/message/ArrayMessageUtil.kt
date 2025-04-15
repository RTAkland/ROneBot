/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 17:43
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.util.message

import cn.rtast.rob.event.raw.message.ArrayMessage
import cn.rtast.rob.event.raw.message.serialize
import cn.rtast.rob.segment.MessageSegment

/**
 * Java专用的[ArrayMessage]工具类
 */
public class ArrayMessageUtil {
    public companion object {
        @JvmStatic
        public fun jvmSerialize(message: List<ArrayMessage>): List<MessageSegment> {
            return message.serialize()
        }

        @JvmStatic
        public fun jvmToSegments(message: List<ArrayMessage>): List<MessageSegment> {
            return jvmSerialize(message)
        }
    }
}