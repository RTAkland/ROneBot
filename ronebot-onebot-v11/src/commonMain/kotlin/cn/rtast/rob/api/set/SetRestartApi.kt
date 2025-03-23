/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.Serializable

@Serializable
internal data class SetRestartApi(val action: String = "set_restart")