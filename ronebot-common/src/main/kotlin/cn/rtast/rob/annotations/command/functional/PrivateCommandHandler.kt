/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.annotations.command.functional

import cn.rtast.rob.annotations.command.functional.session.IEmptyFunctionalCommandHandler
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class PrivateCommandHandler(
    public val aliases: Array<String>,
    public val session: KClass<*> = IEmptyFunctionalCommandHandler::class,
)