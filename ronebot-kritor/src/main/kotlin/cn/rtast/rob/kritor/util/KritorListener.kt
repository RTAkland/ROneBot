/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */


package cn.rtast.rob.kritor.util

import cn.rtast.rob.kritor.kritor.KritorAction
import io.kritor.common.PushMessageBody
import io.kritor.event.EventStructure
import io.kritor.event.NoticeEvent
import io.kritor.event.RequestEvent

interface KritorListener {

    /**
     * 任何接收到的消息都会被分发到此接口上
     */
    suspend fun onMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 未指定的消息事件
     */
    suspend fun onUnspecifiedMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 群聊消息
     */
    suspend fun onGroupMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 私聊消息
     */
    suspend fun onFriendMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 频道消息
     */
    suspend fun onGuildMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 陌生人消息
     */
    suspend fun onStrangerMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 群聊临时会话陌生人消息
     */
    suspend fun onStrangerFromGroupMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 附近的人发送的消息
     */
    suspend fun onNearbyMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 保留: 未被认可的消息
     */
    suspend fun onUnrecognizedMessage(message: PushMessageBody, action: KritorAction) {}

    /**
     * 未知的通知事件
     */
    suspend fun onUnknownNoticeEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 保留: 未被认可的通知
     */
    suspend fun onUnrecognizedNoticeEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 私聊戳一戳
     */
    suspend fun onPrivatePokeEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 私聊撤回消息
     */
    suspend fun onPrivateRecallEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 私聊发送文件
     */
    suspend fun onPrivateFileUploadEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群聊戳一戳
     */
    suspend fun onGroupPokeEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群聊撤回消息
     */
    suspend fun onGroupRecallEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群聊上传的文件
     */
    suspend fun onGroupFileUploadEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群名片被改变
     */
    suspend fun onGroupCardChangedEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群成员的头衔被改变
     */
    suspend fun onGroupMemberUniqueTitleChangedEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 精华消息有变动
     */
    suspend fun onGroupEssenceChangedEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 新成员加入群聊
     */
    suspend fun onGroupMemberIncreaseEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 成员退出群聊
     */
    suspend fun onGroupMemberDecreaseEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群管理员有变动
     */
    suspend fun onGroupAdminChangedEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群打卡
     */
    suspend fun onGroupSignIn(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群成员被禁言
     */
    suspend fun onGroupMemberBannedEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群聊全体禁言
     */
    suspend fun onGroupWholeBannedEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群聊中消息被回应
     */
    suspend fun onGroupReactMessageWithEMOJIEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 群聊被转让
     */
    suspend fun onGroupTransferEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 好友增加
     */
    suspend fun onFriendIncreaseEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 好友减少
     */
    suspend fun onFriendDecreaseEvent(event: NoticeEvent, action: KritorAction) {}

    /**
     * 未知请求
     */
    suspend fun onUnknownRequest(event: RequestEvent, action: KritorAction) {}

    /**
     * 好友申请
     */
    suspend fun onFriendApplyRequest(event: RequestEvent, action: KritorAction) {}

    /**
     * 加群申请
     */
    suspend fun onGroupApplyRequest(event: RequestEvent, action: KritorAction) {}

    /**
     * 被邀请加群
     */
    suspend fun onInvitedGroupRequest(event: RequestEvent, action: KritorAction) {}

    /**
     * 保留: 未被认可的请求
     */
    suspend fun onUnrecognizedRequest(event: RequestEvent, action: KritorAction) {}

    /**
     * Kritor的核心事件
     */
    suspend fun onKritorCoreEvent(event: EventStructure, action: KritorAction) {}
}