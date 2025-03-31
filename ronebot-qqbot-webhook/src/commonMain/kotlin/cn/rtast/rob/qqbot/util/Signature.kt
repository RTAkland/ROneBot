/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.util

import cn.rtast.rob.qqbot.api.SignOutbound
import cn.rtast.rob.qqbot.entity.internal.SignInbound

internal expect fun handleValidation(payload: SignInbound, botSecret: String): SignOutbound