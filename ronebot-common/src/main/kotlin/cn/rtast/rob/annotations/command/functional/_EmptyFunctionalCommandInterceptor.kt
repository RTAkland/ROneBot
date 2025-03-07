/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

@file:Suppress("CLASSNAME")

package cn.rtast.rob.annotations.command.functional

import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.interceptor.IFunctionalLocalCommandInterceptor

/**
 * 默认的空的函数式指令的拦截器
 */
public class _EmptyFunctionalCommandInterceptor : IFunctionalLocalCommandInterceptor<IMessage>