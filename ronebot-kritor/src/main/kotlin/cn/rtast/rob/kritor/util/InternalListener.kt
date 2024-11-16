/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util

import cn.rtast.rob.kritor.BotInstance
import cn.rtast.rob.kritor.kritor.KritorListener
import io.kritor.common.Scene
import io.kritor.event.*

class InternalListener internal constructor(
    private val botInstance: BotInstance,
    private val listener: KritorListener
) {
    suspend fun init(): InternalListener {
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_MESSAGE }
        ).collect { it.onMessage() }
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_NOTICE }
        ).collect { it.onNotice() }
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_REQUEST }
        ).collect { it.onRequest() }
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_CORE_EVENT }
        ).collect { it.onCoreEvent() }
        return this
    }

    private suspend fun EventStructure.onMessage() {
        listener.onMessage(this.message, botInstance.action)
        when (message.scene) {
            Scene.UNSPECIFIED -> listener.onUnspecifiedMessage(this.message, botInstance.action)
            Scene.GROUP -> listener.onGroupMessage(this.message, botInstance.action)
            Scene.FRIEND -> listener.onFriendMessage(this.message, botInstance.action)
            Scene.GUILD -> listener.onGuildMessage(this.message, botInstance.action)
            Scene.STRANGER_FROM_GROUP -> listener.onStrangerFromGroupMessage(this.message, botInstance.action)
            Scene.NEARBY -> listener.onNearbyMessage(this.message, botInstance.action)
            Scene.STRANGER -> listener.onStrangerMessage(this.message, botInstance.action)
            Scene.UNRECOGNIZED -> listener.onUnrecognizedMessage(this.message, botInstance.action)
        }
    }

    private suspend fun EventStructure.onNotice() {
        when (this.notice.type) {
            NoticeEvent.NoticeType.UNKNOWN -> listener.onUnknownNoticeEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.PRIVATE_POKE -> listener.onPrivatePokeEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.PRIVATE_RECALL -> listener.onPrivateRecallEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.GROUP_TRANSFER -> listener.onGroupTransferEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.FRIEND_INCREASE -> listener.onFriendIncreaseEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.FRIEND_DECREASE -> listener.onFriendDecreaseEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.UNRECOGNIZED -> listener.onUnrecognizedNoticeEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.GROUP_POKE -> listener.onGroupPokeEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.GROUP_RECALL -> listener.onGroupRecallEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.GROUP_SIGN_IN -> listener.onGroupSignIn(this.notice, botInstance.action)
            NoticeEvent.NoticeType.GROUP_WHOLE_BAN -> listener.onGroupWholeBannedEvent(this.notice, botInstance.action)
            NoticeEvent.NoticeType.PRIVATE_FILE_UPLOADED -> listener.onPrivateFileUploadEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_FILE_UPLOADED -> listener.onGroupFileUploadEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_CARD_CHANGED -> listener.onGroupCardChangedEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_MEMBER_UNIQUE_TITLE_CHANGED -> listener.onGroupMemberUniqueTitleChangedEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_ESSENCE_CHANGED -> listener.onGroupEssenceChangedEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_MEMBER_INCREASE -> listener.onGroupMemberIncreaseEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_MEMBER_DECREASE -> listener.onGroupMemberDecreaseEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_ADMIN_CHANGED -> listener.onGroupAdminChangedEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_MEMBER_BAN -> listener.onGroupMemberBannedEvent(
                this.notice,
                botInstance.action
            )

            NoticeEvent.NoticeType.GROUP_REACT_MESSAGE_WITH_EMOJI -> listener.onGroupReactMessageWithEMOJIEvent(
                this.notice,
                botInstance.action
            )
        }
    }

    private suspend fun EventStructure.onRequest() {
        when (this.request.type) {
            RequestEvent.RequestType.UNKNOWN -> listener.onUnknownRequest(this.request, botInstance.action)
            RequestEvent.RequestType.FRIEND_APPLY -> listener.onFriendApplyRequest(this.request, botInstance.action)
            RequestEvent.RequestType.GROUP_APPLY -> listener.onGroupApplyRequest(this.request, botInstance.action)
            RequestEvent.RequestType.INVITED_GROUP -> listener.onInvitedGroupRequest(this.request, botInstance.action)
            RequestEvent.RequestType.UNRECOGNIZED -> listener.onUnrecognizedRequest(this.request, botInstance.action)
        }
    }

    private suspend fun EventStructure.onCoreEvent() {
        listener.onKritorCoreEvent(this, botInstance.action)
    }
}