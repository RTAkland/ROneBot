/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


@file:OptIn(InternalROBApi::class)

package cn.rtast.rob

import cn.rtast.rob.annotations.InternalROBApi
import cn.rtast.rob.util.Logger
import cn.rtast.rob.util.getLogger
import kotlinx.coroutines.CoroutineScope

public expect val commonCoroutineScope: CoroutineScope

public val logger: Logger = getLogger()