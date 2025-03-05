/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.annotations.command.functional.PrivateCommandHandler
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.entity.IPrivateSender
import cn.rtast.rob.exceptions.NonFunctionalCommandHandlerException
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

public interface IFunctionalSessionManager<out P : IPrivateMessage,
        out G : IGroupMessage,
        PS : IFunctionalPrivateSession,
        GS : IFunctionalGroupSession,
        GSS : IGroupSender,
        PSS : IPrivateSender> {
    public val privateActiveSessions: MutableMap<PSS, out IFunctionalPrivateSession>
    public val groupActiveSessions: MutableMap<GSS, out IFunctionalGroupSession>

    /**
     * 检查是否传入了没有被`GroupCommandHandler`注解的函数引用
     */
    @Throws(NonFunctionalCommandHandlerException::class)
    public suspend fun inspectGroupCommandHandlerAnnotation(func: KFunction<*>) {
        val annotation = func.findAnnotation<GroupCommandHandler>()
        if (annotation == null) {
            throw NonFunctionalCommandHandlerException()
        }
    }

    /**
     * 检查是否传入了没有被`PrivateCommandHandler`注解的函数引用
     */
    @Throws(NonFunctionalCommandHandlerException::class)
    public suspend fun inspectPrivateCommandHandlerAnnotation(func: KFunction<*>) {
        val annotation = func.findAnnotation<PrivateCommandHandler>()
        if (annotation == null) {
            throw NonFunctionalCommandHandlerException()
        }
    }

    public suspend fun startGroupSession(message: @UnsafeVariance G, command: KFunction<*>): GS

    public suspend fun startPrivateSession(message: @UnsafeVariance P, command: KFunction<*>): PS

    public suspend fun endGroupSession(sender: GSS) {
        groupActiveSessions[sender]?.endSession()
        groupActiveSessions.remove(sender)
    }

    public suspend fun endPrivateSession(sender: PSS) {
        privateActiveSessions[sender]?.endSession()
        privateActiveSessions.remove(sender)
    }

    public suspend fun getPrivateSession(sender: PSS): PS?

    public suspend fun getGroupSession(sender: GSS): GS?
}