/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.segment

import cn.rtast.rob.enums.ArrayMessageType

internal interface BaseSegment {
    val type: ArrayMessageType
}