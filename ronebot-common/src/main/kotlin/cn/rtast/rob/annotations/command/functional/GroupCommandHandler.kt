/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */


package cn.rtast.rob.annotations.command.functional

import cn.rtast.rob.annotations.command.functional.session.EmptyFunctionalCommandHandler
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GroupCommandHandler(
    val aliases: Array<String>,
    val session: KClass<*> = EmptyFunctionalCommandHandler::class
)