/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


package cn.rtast.rob

import cn.rtast.klogging.KLogging
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.util.getLogger
import kotlinx.coroutines.CoroutineScope

@InternalROneBotApi
public expect val commonCoroutineScope: CoroutineScope

//@InternalROneBotApi
//public val logger: KLogging = getLogger()