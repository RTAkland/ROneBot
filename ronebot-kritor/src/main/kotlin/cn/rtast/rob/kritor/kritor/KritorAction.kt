/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */

@file:Suppress("unused")

package cn.rtast.rob.kritor.kritor

import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.kritor.BotInstance
import io.kritor.common.Contact
import io.kritor.common.ForwardMessageBody
import io.kritor.common.Scene
import io.kritor.common.contact
import io.kritor.file.*
import io.kritor.friend.*
import io.kritor.group.*
import io.kritor.guild.*
import io.kritor.message.*
import kotlin.time.Duration

class KritorAction internal constructor(botInstance: BotInstance) {

    private val messageStub = MessageServiceGrpcKt.MessageServiceCoroutineStub(botInstance.interceptedChannel)
    private val friendStub = FriendServiceGrpcKt.FriendServiceCoroutineStub(botInstance.interceptedChannel)
    private val guildStub = GuildServiceGrpcKt.GuildServiceCoroutineStub(botInstance.interceptedChannel)
    private val groupStub = GroupServiceGrpcKt.GroupServiceCoroutineStub(botInstance.interceptedChannel)
    private val groupFileStub = GroupFileServiceGrpcKt.GroupFileServiceCoroutineStub(botInstance.interceptedChannel)

    /**
     * 发送群消息
     */
    suspend fun sendGroupMessage(groupId: Long, message: MessageChain): SendMessageResponse {
        return messageStub.sendMessage(sendMessageRequest {
            this.contact = contact {
                this.scene = Scene.GROUP
                this.peer = groupId.toString()
            }
            this.elements.addAll(message.elements)
        })
    }

    /**
     * 标记一条消息已读
     */
    suspend fun setMessageRead(contact: Contact): SetMessageReadResponse {
        return messageStub.setMessageReaded(setMessageReadRequest {
            this.contact = contact
        })
    }

    /**
     * 撤回消息
     */
    suspend fun recallMessage(contact: Contact, messageId: String): RecallMessageResponse {
        return messageStub.recallMessage(recallMessageRequest {
            this.contact = contact
            this.messageId = messageId
        })
    }

    /**
     * 用表情回应消息
     */
    suspend fun reactMessageWithEmoji(
        contact: Contact,
        messageId: String,
        face: QQFace,
        isSet: Boolean = false
    ): ReactMessageWithEmojiResponse {
        return messageStub.reactMessageWithEmoji(reactMessageWithEmojiRequest {
            this.contact = contact
            this.messageId = messageId
            this.faceId = face.id
            this.isSet = isSet
        })
    }

    /**
     * 获取消息
     */
    suspend fun getMessage(contact: Contact, messageId: String): GetMessageResponse {
        return messageStub.getMessage(getMessageRequest {
            this.contact = contact
            this.messageId = messageId
        })
    }

    /**
     * 通过SeqId来获取消息
     */
    suspend fun getMessageByResId(contact: Contact, messageSeq: Long): GetMessageBySeqResponse {
        return messageStub.getMessageBySeq(getMessageBySeqRequest {
            this.contact = contact
            this.messageSeq = messageSeq
        })
    }

    /**
     * 通过消息id来获取消息记录
     */
    suspend fun getHistoryMessage(
        contact: Contact,
        startMessageId: String = "",
        count: Int = 3
    ): GetHistoryMessageResponse {
        return messageStub.getHistoryMessage(getHistoryMessageRequest {
            this.contact = contact
            this.startMessageId = startMessageId
            this.count = count
        })
    }

    /**
     * 上传转发消息节点
     * WIP
     */
    @Deprecated("Not implemented yet", level = DeprecationLevel.HIDDEN)
    suspend fun uploadForwardMessage(
        contact: Contact,
        messageBody: ForwardMessageBody,
        retryCount: Int = 3
    ): UploadForwardMessageResponse {
        return messageStub.uploadForwardMessage(uploadForwardMessageRequest {
            this.contact = contact
            this.retryCount = retryCount
        })
    }

    /**
     * 获取合并转发消息
     */
    suspend fun downloadForwardMessage(resId: String): DownloadForwardMessageResponse {
        return messageStub.downloadForwardMessage(downloadForwardMessageRequest {
            this.resId = resId
        })
    }

    /**
     * 获取群精华消息列表
     */
    suspend fun getEssenceMessageList(groupId: Long, page: Int, pageSize: Int): GetEssenceMessageListResponse {
        return messageStub.getEssenceMessageList(getEssenceMessageListRequest {
            this.groupId = groupId
            this.page = page
            this.pageSize = pageSize
        })
    }

    /**
     * 设置群精华消息
     */
    suspend fun setEssenceMessage(groupId: Long, messageId: String): SetEssenceMessageResponse {
        return messageStub.setEssenceMessage(setEssenceMessageRequest {
            this.groupId = groupId
            this.messageId = messageId
        })
    }

    /**
     * 删除群精华消息
     */
    suspend fun deleteEssenceMessage(groupId: Long, messageId: String): DeleteEssenceMessageResponse {
        return messageStub.deleteEssenceMessage(deleteEssenceMessageRequest {
            this.messageId = messageId
            this.groupId = groupId
        })
    }

    /**
     * 获取好友列表
     */
    suspend fun getFriendList(refresh: Boolean = false): GetFriendListResponse {
        return friendStub.getFriendList(getFriendListRequest {
            this.refresh = refresh
        })
    }

    /**
     * 设置个人资料
     */
    suspend fun setProfileCard(
        nickname: String = "",
        company: String = "",
        email: String = "",
        college: String = "",
        personalNote: String = "",
        birthday: Int = 1,
        age: Int = 1
    ): SetProfileCardResponse {
        return friendStub.setProfileCard(setProfileCardRequest {
            this.age = age
            this.company = company
            this.email = email
            this.college = college
            this.nick = nickname
            this.birthday = birthday
            this.personalNote = personalNote
        })
    }

    /**
     * 通过Uid判断用户是否在黑名单内
     */
    suspend fun isBlockListUser(targetUid: String): IsBlackListUserResponse {
        return friendStub.isBlackListUser(isBlackListUserRequest {
            this.targetUid = targetUid
        })
    }

    /**
     * 通过Uin判断用户是否在黑名单内
     */
    suspend fun isBlockListUser(targetUin: Long): IsBlackListUserResponse {
        return friendStub.isBlackListUser(isBlackListUserRequest {
            this.targetUin = targetUin
        })
    }

    /**
     * 通过Uid点赞用户
     */
    suspend fun voteUser(targetUid: String): VoteUserResponse {
        return friendStub.voteUser(voteUserRequest {
            this.targetUid = targetUid
        })
    }

    /**
     * 通过Uin点赞用户
     */
    suspend fun voteUser(targetUin: Long): VoteUserResponse {
        return friendStub.voteUser(voteUserRequest {
            this.targetUin = targetUin
        })
    }

    /**
     * 通过Uid禁言用户
     */
    suspend fun banMember(groupId: Long, targetUid: String, duration: Duration): BanMemberResponse {
        return groupStub.banMember(banMemberRequest {
            this.groupId = groupId
            this.targetUid = targetUid
            this.duration = duration.inWholeSeconds.toInt()
        })
    }

    /**
     * 通过Uin禁言用户
     */
    suspend fun banMember(groupId: Long, targetUin: Long, duration: Duration): BanMemberResponse {
        return groupStub.banMember(banMemberRequest {
            this.groupId = groupId
            this.targetUin = targetUin
            this.duration = duration.inWholeSeconds.toInt()
        })
    }

    /**
     * 通过Uid戳一戳用户
     */
    suspend fun pokeMember(groupId: Long, targetUid: String): PokeMemberResponse {
        return groupStub.pokeMember(pokeMemberRequest {
            this.groupId = groupId
            this.targetUid = targetUid
        })
    }

    /**
     * 通过Uin戳一戳用户
     */
    suspend fun pokeMember(groupId: Long, targetUin: Long): PokeMemberResponse {
        return groupStub.pokeMember(pokeMemberRequest {
            this.groupId = groupId
            this.targetUin = targetUin
        })
    }

    /**
     * 通过Uid踢出用户
     */
    suspend fun kickMember(groupId: Long, targetUid: String): KickMemberResponse {
        return groupStub.kickMember(kickMemberRequest {
            this.groupId = groupId
            this.targetUid = targetUid
        })
    }

    /**
     * 通过Uin踢出用户
     */
    suspend fun kickMember(groupId: Long, targetUin: Long): KickMemberResponse {
        return groupStub.kickMember(kickMemberRequest {
            this.groupId = groupId
            this.targetUin = targetUin
        })
    }

    /**
     * 退出群聊
     */
    suspend fun leaveGroup(groupId: Long): LeaveGroupResponse {
        return groupStub.leaveGroup(leaveGroupRequest {
            this.groupId = groupId
        })
    }

    /**
     * 通过Uid修改用户名片
     */
    suspend fun modifyMemberCard(groupId: Long, targetUid: String, card: String): ModifyMemberCardResponse {
        return groupStub.modifyMemberCard(modifyMemberCardRequest {
            this.groupId = groupId
            this.targetUid = targetUid
            this.card = card
        })
    }

    /**
     * 通过Uin修改用户名片
     */
    suspend fun modifyMemberCard(groupId: Long, targetUin: Long, card: String): ModifyMemberCardResponse {
        return groupStub.modifyMemberCard(modifyMemberCardRequest {
            this.groupId = groupId
            this.targetUin = targetUin
            this.card = card
        })
    }

    /**
     * 修改群名字
     */
    suspend fun modifyGroupName(groupId: Long, newGroupName: String): ModifyGroupNameResponse {
        return groupStub.modifyGroupName(modifyGroupNameRequest {
            this.groupId = groupId
            this.groupName = newGroupName
        })
    }

    /**
     * 修改群备注
     */
    suspend fun modifyGroupRemark(groupId: Long, newGroupRemark: String): ModifyGroupRemarkResponse {
        return groupStub.modifyGroupRemark(modifyGroupRemarkRequest {
            this.groupId = groupId
            this.remark = newGroupRemark
        })
    }

    /**
     * 通过Uid设置群管理员
     */
    suspend fun getGroupAdmin(groupId: Long, targetUid: String, isAdmin: Boolean = true): SetGroupAdminResponse {
        return groupStub.setGroupAdmin(setGroupAdminRequest {
            this.groupId = groupId
            this.targetUid = targetUid
            this.isAdmin = isAdmin
        })
    }

    /**
     * 通过Uin设置群管理员
     */
    suspend fun getGroupAdmin(groupId: Long, targetUin: Long, isAdmin: Boolean = true): SetGroupAdminResponse {
        return groupStub.setGroupAdmin(setGroupAdminRequest {
            this.groupId = groupId
            this.targetUin = targetUin
            this.isAdmin = isAdmin
        })
    }

    /**
     * 通过Uid设置群成员头衔
     */
    suspend fun setGroupUniqueTitle(
        groupId: Long,
        targetUid: String,
        uniqueTitle: String
    ): SetGroupUniqueTitleResponse {
        return groupStub.setGroupUniqueTitle(setGroupUniqueTitleRequest {
            this.groupId = groupId
            this.targetUid = targetUid
            this.uniqueTitle = uniqueTitle
        })
    }

    /**
     * 通过Uin设置群成员头衔
     */
    suspend fun setGroupUniqueTitle(groupId: Long, targetUin: Long, uniqueTitle: String): SetGroupUniqueTitleResponse {
        return groupStub.setGroupUniqueTitle(setGroupUniqueTitleRequest {
            this.groupId = groupId
            this.targetUin = targetUin
            this.uniqueTitle = uniqueTitle
        })
    }

    /**
     * 设置全体禁言
     */
    suspend fun setGroupWholeBan(groupId: Long, isBan: Boolean = true): SetGroupWholeBanResponse {
        return groupStub.setGroupWholeBan(setGroupWholeBanRequest {
            this.isBan = isBan
        })
    }

    /**
     * 获取群信息
     */
    suspend fun getGroupInfo(groupId: Long): GetGroupInfoResponse {
        return groupStub.getGroupInfo(getGroupInfoRequest {
            this.groupId = groupId
        })
    }

    /**
     * 获取群列表
     */
    suspend fun getGroupList(refresh: Boolean = false): GetGroupListResponse {
        return groupStub.getGroupList(getGroupListRequest {
            this.refresh = refresh
        })
    }

    /**
     * 通过Uid获取群成员信息
     */
    suspend fun getGroupMemberInfo(
        groupId: Long,
        targetUid: String,
        refresh: Boolean = false
    ): GetGroupMemberInfoResponse {
        return groupStub.getGroupMemberInfo(getGroupMemberInfoRequest {
            this.groupId = groupId
            this.targetUid = targetUid
            this.refresh = refresh
        })
    }

    /**
     * 通过Uin获取群成员信息
     */
    suspend fun getGroupMemberInfo(
        groupId: Long,
        targetUin: Long,
        refresh: Boolean = false
    ): GetGroupMemberInfoResponse {
        return groupStub.getGroupMemberInfo(getGroupMemberInfoRequest {
            this.groupId = groupId
            this.targetUin = targetUin
            this.refresh = refresh
        })
    }

    /**
     * 获取群成员列表
     */
    suspend fun getGroupMemberList(groupId: Long, refresh: Boolean = false): GetGroupMemberListResponse {
        return groupStub.getGroupMemberList(getGroupMemberListRequest {
            this.groupId = groupId
            this.refresh = refresh
        })
    }

    /**
     * 获取被禁言的群成员列表
     */
    suspend fun getProhibitedUserList(groupId: Long): GetProhibitedUserListResponse {
        return groupStub.getProhibitedUserList(getProhibitedUserListRequest {
            this.groupId = groupId
        })
    }

    /**
     * 获取@全体成员次数
     */
    suspend fun getRemainCountAtAll(groupId: Long): GetRemainCountAtAllResponse {
        return groupStub.getRemainCountAtAll(getRemainCountAtAllRequest {
            this.groupId = groupId
        })
    }

    /**
     * 获取尚未加入的群聊信息
     */
    suspend fun getNotJoinedGroupInfo(groupId: Long): GetNotJoinedGroupInfoResponse {
        return groupStub.getNotJoinedGroupInfo(getNotJoinedGroupInfoRequest {
            this.groupId = groupId
        })
    }

    /**
     * 获取群荣誉列表
     */
    suspend fun getGroupHonor(groupId: Long, refresh: Boolean = false): GetGroupHonorResponse {
        return groupStub.getGroupHonor(getGroupHonorRequest {
            this.groupId = groupId
            this.refresh = refresh
        })
    }

    /**
     * 创建文件夹
     */
    suspend fun createFolder(groupId: Long, name: String): CreateFolderResponse {
        return groupFileStub.createFolder(createFolderRequest {
            this.groupId = groupId
            this.name = name
        })
    }

    /**
     * 删除文件夹
     */
    suspend fun deleteFolder(groupId: Long, folderId: String): DeleteFolderResponse {
        return groupFileStub.deleteFolder(deleteFolderRequest {
            this.groupId = groupId
            this.folderId = folderId
        })
    }

    /**
     * 重命名文件夹
     */
    suspend fun renameFolder(groupId: Long, folderId: String, newName: String): RenameFolderResponse {
        return groupFileStub.renameFolder(renameFolderRequest {
            this.groupId = groupId
            this.folderId = folderId
            this.name = newName
        })
    }

    /**
     * 获取文件系统信息，例如文件数量，空间大小限制
     */
    suspend fun getFileSystemInfo(groupId: Long): GetFileSystemInfoResponse {
        return groupFileStub.getFileSystemInfo(getFileSystemInfoRequest {
            this.groupId = groupId
        })
    }

    /**
     * 获取文件列表
     */
    suspend fun getFileList(groupId: Long, folderId: String? = null): GetFileListResponse {
        return groupFileStub.getFileList(getFileListRequest {
            this.groupId = groupId
            folderId?.let { this.folderId = it }
        })
    }

    /**
     * 该接口用于获取频道系统内BOT的资料
     */
    suspend fun getBotInfo(): GetBotInfoResponse {
        return guildStub.getBotInfo(getBotInfoRequest {})
    }

    /**
     * 获取频道列表
     */
    suspend fun getChannelList(): GetChannelListResponse {
        return guildStub.getChannelList(getChannelListRequest {})
    }

    /**
     * 获取频道元数据，例如当前成员数量之类
     */
    suspend fun getGuildMetaByGuest(guildId: Long): GetGuildMetaByGuestResponse {
        return guildStub.getGuildMetaByGuest(getGuildMetaByGuestRequest {
            this.guildId = guildId
        })
    }

    /**
     * 获取一个频道的子频道(channel)列表
     */
    suspend fun getGuildChannelList(guildId: Long, refresh: Boolean = false): GetGuildChannelListResponse {
        return guildStub.getGuildChannelList(getGuildChannelListRequest {
            this.guildId = guildId
            this.refresh = refresh
        })
    }

    /**
     * 获取一个频道成员列表，但是因为数据量大，可能需要分页
     */
    suspend fun getGuildMemberList(
        guildId: Long,
        nextToken: String? = null,
        all: Boolean = false,
        refresh: Boolean = false
    ): GetGuildMemberListResponse {
        return guildStub.getGuildMemberList(getGuildMemberListRequest {
            this.guildId = guildId
            nextToken?.let { this.nextToken = it }
            this.all = all
            this.refresh = refresh
        })
    }

    /**
     * 单独获取频道成员信息，附带有权限信息和身份组
     */
    suspend fun getGuildMember(guildId: Long, tinyId: Long): GetGuildMemberResponse {
        return guildStub.getGuildMember(getGuildMemberRequest {
            this.guildId = guildId
            this.tinyId = tinyId
        })
    }

    /**
     * 发送频道内信息，需要单独的API哦，不要使用/send_message去发频道消息，发不出去的
     */
    suspend fun sendChannelMessage(
        guildId: Long,
        channelId: Long,
        message: String,
        retryCount: Int = 2,
        recallDuration: Duration
    ): SendChannelMessageResponse {
        return guildStub.sendChannelMessage(sendChannelMessageRequest {
            this.guildId = guildId
            this.message = message
            this.channelId = channelId
            this.retryCnt = retryCount
            this.recallDuration = recallDuration.inWholeSeconds
        }
        )
    }

    /**
     * 新的获取帖子广场的帖子
     */
    suspend fun getGuildFeedList(guildId: Long, from: Int): GetGuildFeedListResponse {
        return guildStub.getGuildFeedList(getGuildFeedListRequest {
            this.guildId = guildId
            this.from = from
        })
    }

    /**
     * 获取身份组列表，包括隐藏的身份组
     */
    suspend fun getGuildRoleList(guildId: Long): GetGuildRoleListResponse {
        return guildStub.getGuildRoleList(getGuildRoleListRequest {
            this.guildId = guildId
        })
    }

    /**
     * 删除一个身份组，首先，你得保证你有权限，因为这个API不会提供任何返回数据
     */
    suspend fun deleteGuildRole(guildId: Long, roleId: Long): DeleteGuildRoleResponse {
        return guildStub.deleteGuildRole(deleteGuildRoleRequest {
            this.guildId = guildId
            this.roleId = roleId
        }
        )
    }
}