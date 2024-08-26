/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.out.GroupMessageOut
import cn.rtast.rob.entity.out.PrivateMessageOut
import cn.rtast.rob.entity.out.RevokeMessageOut
import cn.rtast.rob.util.toJson
import org.java_websocket.WebSocket

interface OBAction {
    fun sendGroupMessage(websocket: WebSocket, groupId: Long, content: String) {
        // do not inheritance this function, or you know what you are doing!
        websocket.send(GroupMessageOut(params = GroupMessageOut.Params(groupId, content)).toJson())
    }

    fun sendPrivateMessage(websocket: WebSocket, userId: Long, content: String) {
        // do not inheritance this function, or you know what you are doing!
        websocket.send(PrivateMessageOut(params = PrivateMessageOut.Params(userId, content)).toJson())
    }

    fun revokeMessage(websocket: WebSocket, messageId: Long) {
        TODO("Not impl")
        websocket.send(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)).toJson())
    }
}