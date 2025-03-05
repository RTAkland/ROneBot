/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */


package cn.rtast.rob.annotations.command.functional

import cn.rtast.rob.annotations.command.functional.session.IEmptyFunctionalCommandHandler
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.interceptor.IFunctionalLocalCommandInterceptor
import kotlin.reflect.KClass

/**
 * 用于注解一个方法上将其标记为命令处理器
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class GroupCommandHandler(
    public val aliases: Array<String>,
    public val session: KClass<*> = IEmptyFunctionalCommandHandler::class
)

/**
 * 用于注解一个方法上将其标记为命令处理器并且附带一个拦截器
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class GroupCommandHandlerIntercepted(
    public val aliases: Array<String>,
    public val interceptor: KClass<out IFunctionalLocalCommandInterceptor<out IGroupMessage>>,
    public val session: KClass<*> = IEmptyFunctionalCommandHandler::class,
)