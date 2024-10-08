/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.util

import cn.rtast.rob.common.util.toJson
import cn.rtast.rob.satori.RSatoriFactory

interface SatoriListener {
    suspend fun onReady()
}