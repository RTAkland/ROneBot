/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.ArrayMessageType

/**
 * 内部使用的Segment作为超类
 */
internal interface InternalBaseSegment {
    val type: ArrayMessageType
}

/**
 * 用户可以使用的Segment作为超类
 */
interface Segment