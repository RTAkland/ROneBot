/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:57 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.milky

import cn.rtast.rob.entity.Resource
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.api.friend.SendFriendPokeAPI
import cn.rtast.rob.milky.api.friend.SendProfileLikeAPI
import cn.rtast.rob.milky.api.group.*
import cn.rtast.rob.milky.api.request.AcceptRequestAPI
import cn.rtast.rob.milky.api.request.RejectRequestAPI
import cn.rtast.rob.milky.api.system.*
import cn.rtast.rob.milky.enums.internal.APIEndpoint
import cn.rtast.rob.milky.event.common.Friend
import cn.rtast.rob.milky.event.common.Group
import cn.rtast.rob.milky.event.common.GroupMember
import cn.rtast.rob.milky.event.system.*
import cn.rtast.rob.milky.util.requestAPI
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

public class MilkyAction internal constructor(
    public val botInstance: BotInstance,
) {
    private suspend inline fun <reified T, K> api(endpoint: APIEndpoint, payload: K? = null): T =
        botInstance.requestAPI<T, K>(endpoint, payload)

    private suspend fun <K> api(endpoint: APIEndpoint, payload: K? = null) =
        botInstance.requestAPI<K>(endpoint, payload)

    /**
     * 获取好友列表
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getFriendList(nocache: Boolean = false): List<Friend> {
        return this.api<GetFriendList, GetFriendListAPI>(
            APIEndpoint.System.GetFriendList,
            GetFriendListAPI(nocache)
        ).data
    }

    /**
     * 获取好友信息
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getFriendInfo(userId: Long, noCache: Boolean = false): Friend {
        return this.api<GetFriendInfo, GetFriendInfoAPI>(
            APIEndpoint.System.GetFriendInfo,
            GetFriendInfoAPI(userId, noCache)
        ).data
    }

    /**
     * 获取群聊信息
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): Group {
        return this.api<GetGroupInfo, GetGroupInfoAPI>(
            APIEndpoint.System.GetGroupInfo,
            GetGroupInfoAPI(groupId, noCache)
        ).data
    }

    /**
     * 获取群聊列表
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupList(noCache: Boolean = false): List<Group> {
        return this.api<GetGroupList, GetGroupListAPI>(
            APIEndpoint.System.GetGroupList,
            GetGroupListAPI(noCache)
        ).data
    }

    /**
     * 获取群成员信息
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false): GroupMember {
        return this.api<GetGroupMemberInfo, GetGroupMemberInfoAPI>(
            APIEndpoint.System.GetGroupMemberInfo,
            GetGroupMemberInfoAPI(groupId, userId, noCache)
        ).data
    }

    /**
     * 获取群成员列表
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupMemberList(groupId: Long, noCache: Boolean = false): List<GroupMember> {
        return this.api<GetGroupMemberList, GetGroupMemberListAPI>(
            APIEndpoint.System.GetGroupMemberList,
            GetGroupMemberListAPI(groupId, noCache)
        ).data
    }

    /**
     * 同意请求
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun acceptRequest(requestId: String) {
        this.api<AcceptRequestAPI>(APIEndpoint.Request.AcceptRequest, AcceptRequestAPI(requestId))
    }

    /**
     * 拒绝请求
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun rejectRequest(requestId: String, reason: String = "") {
        this.api<RejectRequestAPI>(APIEndpoint.Request.RejectRequest, RejectRequestAPI(requestId, reason))
    }

    /**
     * 发送好友戳一戳
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendFriendPoke(userId: Long, isSelf: Boolean = false) {
        this.api<SendFriendPokeAPI>(APIEndpoint.Friend.SendFriendPoke, SendFriendPokeAPI(userId, isSelf))
    }

    /**
     * 点赞对方资料卡
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendProfileLike(userId: Long, count: Int = 1) {
        this.api<SendProfileLikeAPI>(APIEndpoint.Friend.SendProfileLike, SendProfileLikeAPI(userId, count))
    }

    /**
     * 设置群名称
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupName(groupId: Long, name: String) {
        this.api<SetGroupNameAPI>(APIEndpoint.Group.SetGroupName, SetGroupNameAPI(groupId, name))
    }

    /**
     * 设置群头像
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupAvatar(groupId: Long, imageResource: Resource) {
        this.api<SetGroupAvatarAPI>(
            APIEndpoint.Group.SetGroupAvatar,
            SetGroupAvatarAPI(groupId, imageResource.toString())
        )
    }

    /**
     * 设置群成员昵称
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String) {
        this.api<SetGroupMemberCardAPI>(
            APIEndpoint.Group.SetGroupMemberCard,
            SetGroupMemberCardAPI(groupId, userId, card)
        )
    }

    /**
     * 设置群成员头衔
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberSpecialTitle(groupId: Long, userId: Long, specialTitle: String) {
        this.api<SetGroupMemberSpecialTitleAPI>(
            APIEndpoint.Group.SetGroupMemberSpecialTitle,
            SetGroupMemberSpecialTitleAPI(groupId, userId, specialTitle)
        )
    }

    /**
     * 将群成员设置/取消管理员
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberAdmin(groupId: Long, userId: Long, isSet: Boolean = true) {
        this.api<SetGroupMemberAdminAPI>(
            APIEndpoint.Group.SetGroupMemberAdmin,
            SetGroupMemberAdminAPI(groupId, userId, isSet)
        )
    }

    /**
     * 禁言/解除禁言群成员
     * 0秒表示解除禁言
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberMute(groupId: Long, userId: Long, duration: Duration = 0.seconds) {
        this.api<SetGroupMemberMuteAPI>(
            APIEndpoint.Group.SetGroupMemberMute,
            SetGroupMemberMuteAPI(groupId, userId, duration.inWholeSeconds.toInt())
        )
    }

    /**
     * 禁言/解除禁言群成员
     * 使用整形来表示
     * 0秒表示解除禁言
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberMute(groupId: Long, userId: Long, duration: Int = 0): Unit =
        setGroupMemberMute(groupId, userId, duration.seconds)


    /**
     * 开启全体禁言
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupWholeMute(groupId: Long, isMute: Boolean = true) {
        this.api<SetGroupWholeMuteAPI>(APIEndpoint.Group.SetGroupWholeMute, SetGroupWholeMuteAPI(groupId, isMute))
    }

    /**
     * 踢出群成员
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun kickGroupMember(groupId: Long, userId: Long, rejectAddRequest: Boolean = true) {
        this.api<KickGroupMemberAPI>(
            APIEndpoint.Group.KickGroupMember,
            KickGroupMemberAPI(groupId, userId, rejectAddRequest)
        )
    }

    /**
     * 获取公告列表
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupAnnouncementList(groupId: Long) {
        TODO("")
    }

    /**
     * 发布群公告
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupAnnouncement(groupId: Long, content: String, imageResource: Resource) {
        this.api<SendGroupAnnouncementAPI>(
            APIEndpoint.Group.SendGroupAnnouncement,
            SendGroupAnnouncementAPI(groupId, content, imageResource.toString())
        )
    }

    /**
     * 删除群公告
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun deleteGroupAnnouncement(groupId: Long, announcementId: Long) {
        this.api<DeleteGroupAnnouncementAPI>(
            APIEndpoint.Group.DeleteGroupAnnouncement,
            DeleteGroupAnnouncementAPI(groupId, announcementId)
        )
    }

    /**
     * 退出群聊
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun quitGroup(groupId: Long) {
        this.api<QuitGroupAPI>(APIEndpoint.Group.QuitGroup, QuitGroupAPI(groupId))
    }

    /**
     * 发送群消息表情回应
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMessageReaction(
        groupId: Long,
        messageSeq: Long,
        reactionId: String,
        isAdd: Boolean = true
    ) {
        this.api<SendGroupMessageReactionAPI>(
            APIEndpoint.Group.SendGroupMessageReaction,
            SendGroupMessageReactionAPI(groupId, messageSeq, reactionId, isAdd)
        )
    }

    /**
     * 发送群消息表情回应的简化别名
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun reaction(groupId: Long, messageSeq: Long, reactionId: String, isAdd: Boolean = true): Unit =
        sendGroupMessageReaction(groupId, messageSeq, reactionId, isAdd)

    /**
     * 发送群戳一戳
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupPoke(groupId: Long, userId: Long) {
        this.api<SendGroupPokeAPI>(APIEndpoint.Group.SendGroupPoke, SendGroupPokeAPI(groupId, userId))
    }

    // Done system, friend, request, group
}