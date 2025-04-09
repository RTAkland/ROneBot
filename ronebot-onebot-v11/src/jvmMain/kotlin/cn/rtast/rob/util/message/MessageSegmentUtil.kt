/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 17:46
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.util.message

import cn.rtast.jvmonly.linter.JvmOnly
import cn.rtast.rob.segment.MessageSegment
import kotlin.jvm.JvmStatic
import kotlin.reflect.KClass

public class MessageSegmentUtil {
    public companion object {
        @JvmOnly
        @JvmStatic
        public fun has(segments: List<MessageSegment>, clazz: KClass<out MessageSegment>): Boolean {
            return segments.any { clazz.isInstance(it) }
        }
    }
}