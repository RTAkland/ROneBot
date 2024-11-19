/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.interceptor

import cn.rtast.rob.interceptor.IExecutionInterceptor
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.util.BaseCommand

abstract class ExecutionInterceptor :
    IExecutionInterceptor<BaseCommand, GroupAtMessageCreateEvent, C2CMessageCreateEvent>

internal val defaultInterceptor = object : ExecutionInterceptor() {}