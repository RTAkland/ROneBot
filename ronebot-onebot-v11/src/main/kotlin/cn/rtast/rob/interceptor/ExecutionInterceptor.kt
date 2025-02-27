/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.interceptor

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage


/**
 * 实现了拦截器
 */
abstract class ExecutionInterceptor :
    IExecutionInterceptor<BaseCommand, GroupMessage, PrivateMessage>

/**
 * 当用户没有设置指令拦截器时使用默认的拦截器
 * 即: 继续执行任何指令, 执行完成之后不做任何操作
 */
internal val defaultInterceptor = object : ExecutionInterceptor() {}
