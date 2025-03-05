/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.interceptor

import cn.rtast.rob.interceptor.ICommandInterceptor
import cn.rtast.rob.qqbot.command.BaseCommand
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent

public abstract class ExecutionInterceptor :
    ICommandInterceptor<BaseCommand, GroupAtMessageCreateEvent, C2CMessageCreateEvent>

internal val defaultInterceptor = object : ExecutionInterceptor() {}