/*
 * Copyright © 2025 RTAkland
 * Date: 10/7/25, 6:22 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.command.session

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.command.CommandRequest
import cn.rtast.rob.milky.command.TempSession
import cn.rtast.rob.milky.event.ws.raw.ReceiveMessage
import cn.rtast.rob.session.GroupSession
import cn.rtast.rob.session.GroupSessionStruct
import cn.rtast.rob.session.PrivateSession
import cn.rtast.rob.session.PrivateSessionStruct

@OptIn(ExperimentalROneBotApi::class)
public suspend fun CommandRequest.startGroupSession(block: suspend (GroupSessionStruct<ReceiveMessage>) -> Unit) {
    val session = GroupSession(message, block, message.group!!.groupId)
    MilkyBotFactory.sessionManager.startGroupSession(message, session)
    session.__finished.await()
}

@OptIn(ExperimentalROneBotApi::class)
public suspend fun CommandRequest.startPrivateSession(block: suspend (PrivateSessionStruct<ReceiveMessage>) -> Unit) {

    val session = PrivateSession(block, message)
    MilkyBotFactory.sessionManager.startPrivateSession(message, session)
    session.__finished.await()
}

@OptIn(ExperimentalROneBotApi::class)
public suspend fun CommandRequest.startTempSession(block: suspend (TempSession) -> Unit): Unit =
    startPrivateSession(block)