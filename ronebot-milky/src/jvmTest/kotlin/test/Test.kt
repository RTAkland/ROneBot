/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.ws.packed.*
import cn.rtast.rob.milky.milky.MilkyListener
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class Test {
    @Test
    fun `test api`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot(
                "http://127.0.0.1:3000", "114514",
                logLevel = LogLevel.DEBUG,
                listener = object : MilkyListener {
                    override suspend fun onGroupMessageEvent(event: GroupMessageEvent) {
                        event.group.groupId
                    }

                    override suspend fun onConnected(event: WebsocketConnectedEvent) {
                        println(event)
                    }

                    override suspend fun onDisconnected(event: WebsocketDisconnectedEvent) {
                        println(event)
                    }

                    override suspend fun onFriendFileUploadEvent(event: FriendFileUploadEvent) {
                        println(event)
                    }

                    override suspend fun onFriendNudgeEvent(event: FriendNudgeEvent) {
                        println(event)
                    }

                    override suspend fun onFriendRequest(event: FriendRequestEvent) {
                        println(event)
                    }

                    override suspend fun onGroupAdminChangeEvent(event: GroupAdminChangeEvent) {
                        println(event)
                    }

                    override suspend fun onGroupEssenceMessageChangeEvent(event: GroupEssenceMessageChangeEvent) {
                        println(event)
                    }

                    override suspend fun onGroupFileUploadEvent(event: GroupFileUploadEvent) {
                        println(event)
                    }

                    override suspend fun onGroupInvitationRequestEvent(event: GroupInvitationRequestEvent) {
                        println(event)
                    }

                    override suspend fun onGroupInvitedJoinRequestEvent(event: GroupInvitedJoinRequestEvent) {
                        println(event)
                    }

                    override suspend fun onGroupInvitedJoinRequestEventJvm(event: GroupInvitedJoinRequestEvent) {
                        println(event)
                    }

                    override suspend fun onGroupJoinRequestEvent(event: GroupJoinRequestEvent) {
                        println(event)
                    }

                    override suspend fun onGroupMemberDecreaseEvent(event: GroupMemberDecreaseEvent) {
                        println(event)
                    }

                    override suspend fun onGroupMemberIncreaseEvent(event: GroupMemberIncreaseEvent) {
                        println(event)
                    }

                    override suspend fun onGroupMessageReactionEvent(event: GroupMessageReactionEvent) {
                        println(event)
                    }

                    override suspend fun onGroupMuteEvent(event: GroupMuteEvent) {
                        println(event)
                    }

                    override suspend fun onGroupNameChangeEvent(event: GroupNameChangeEvent) {
                        println(event)
                    }

                    override suspend fun onGroupNudgeEvent(event: GroupNudgeEvent) {
                        println(event)
                    }

                    override suspend fun onGroupWholeMuteEvent(event: GroupWholeMuteEvent) {
                        println(event)
                    }

                    override suspend fun onRawMessage(event: RawMessageEvent) {
                        println(event)
                    }

                    override suspend fun onMessageReceive(event: MessageReceiveEvent) {
                        println(event)
                    }

                    override suspend fun onPrivateMessageEvent(event: PrivateMessageEvent) {
                        println(event)
                    }

                    override suspend fun onMessageRecall(event: MessageRecallEvent) {
                        println(event)
                    }
                }
            )
        }
        while (true) {
        }
    }
}