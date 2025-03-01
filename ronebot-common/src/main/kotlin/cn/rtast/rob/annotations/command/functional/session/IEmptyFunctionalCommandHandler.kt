/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

@file:Suppress("unused")

package cn.rtast.rob.annotations.command.functional.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage

class IEmptyFunctionalCommandHandler {

    @GroupSessionHandler
    suspend fun emptyGroupHandler(message: IGroupMessage) {
        println(message.sessionId)
    }

    @PrivateSessionHandler
    suspend fun emptyPrivateHandler(message: IPrivateMessage) {
        println(message.sessionId)
    }
}