/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.exceptions

/**
 * 如果传入了一个没有被`GroupCommandHandler`或`PrivateCommandHandler`注解的函数引用
 * 则会抛出磁异常
 * @see cn.rtast.rob.session.IFunctionalSessionManager.inspectGroupCommandHandlerAnnotation
 * @see cn.rtast.rob.session.IFunctionalSessionManager.inspectPrivateCommandHandlerAnnotation
 */
public class NonFunctionalCommandHandlerException : Exception("这不是一个函数式命令处理器的函数")