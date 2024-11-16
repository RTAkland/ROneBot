/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.satori

import cn.rtast.rob.common.ext.Http
import cn.rtast.rob.common.ext.SendActionExt
import cn.rtast.rob.satori.BotInstance
import cn.rtast.rob.satori.entity.guild.inbound.*
import cn.rtast.rob.satori.entity.guild.outbound.*
import cn.rtast.rob.satori.enums.GuildUserRole
import cn.rtast.rob.util.fromArrayJson
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import kotlin.time.Duration

class SatoriAction internal constructor(
    private val botInstance: BotInstance,
) : SendActionExt {
    override suspend fun send(api: String, payload: Any?): String {
        val newPayload = payload?.toJson() ?: "{}"
        return Http.post(
            "${botInstance.apiAddress}/v1/$api", newPayload, mapOf(
                "Authorization" to "Bearer ${botInstance.apiAccessToken}",
                "Satori-User-ID" to botInstance.botUserId,
                "Satori-Platform" to botInstance.botPlatforms.platformName
            )
        )
    }

    /**
     * 调用没有列出来的API
     */
    suspend fun callApi(endpoint: String, params: Map<String, Any>): String {
        return this.send(endpoint, params)
    }

    /**
     * 获取一个群组信息
     */
    suspend fun getGuild(guildId: String): GetGuild {
        val payload = GetGuildOutbound(guildId)
        return this.send("guild.get", payload).fromJson<GetGuild>()
    }

    /**
     * 获取账号下所有的群组
     */
    suspend fun getGuildList(): List<GetGuild> {
        return this.send("guild.list", "").fromJson<GetGuildList>().data
    }

    /**
     * 同意加群请求
     */
    suspend fun approveGuildRequest(id: String, approve: Boolean = true, comment: String? = null) {
        val payload = ApproveGuildRequestOutbound(id, approve, comment)
        this.send("guild.approve", payload)
    }

    /**
     * 获取群组成员信息
     */
    suspend fun getGuildMember(guildId: String, userId: String): GetGuildMember {
        val payload = GetGuildMemberOutbound(guildId, userId)
        return this.send("guild.member.get", payload).fromJson<GetGuildMember>()
    }

    /**
     * 获取群组成员列表
     */
    suspend fun getGuildMemberList(guildId: String): List<GetGuildMember> {
        val payload = GetGuildMemberListOutbound(guildId)
        return this.send("guild.member.list", payload).fromJson<GetGuildMemberList>().data
    }

    /**
     * 踢出群组成员
     */
    suspend fun kickGuildMember(guildId: String, userId: String, permanent: Boolean = false) {
        val payload = KickGuildMemberOutbound(guildId, userId, permanent)
        this.send("guild.member.kick", payload)
    }

    /**
     * 禁言群组成员
     */
    suspend fun muteGuildMember(guildId: String, userId: String, duration: Duration) {
        val payload = MuteGuildMemberOutbound(guildId, userId, duration.inWholeMilliseconds)
        this.send("guild.member.mute", payload)
    }

    /**
     * 同意群组邀请请求
     */
    suspend fun approveInvite(id: String, approve: Boolean, comment: String? = null) {
        val payload = ApproveGuildRequestOutbound(id, approve, comment)
        this.send("guild.member.approve", payload)
    }

    /**
     * 获取群组角色信息
     */
    suspend fun getGuildRole(guildId: String): List<GetGuildRole.Role> {
        val payload = GetGuildRoleOutbound(guildId)
        return this.send("guild.role.list", payload).fromJson<GetGuildRole>().data
    }

    /**
     * 设置群组成员的角色信息
     */
    suspend fun setGuildMemberRole(guildId: String, userId: String, role: GuildUserRole) {
        val payload = SetGuildMemberRole(guildId, userId, role.roleId)
        this.send("guild.member.role.set", payload)
    }

    /**
     * 取消设置群组成员的角色信息
     */
    suspend fun unsetGuildMemberRole(guildId: String, userId: String, role: GuildUserRole) {
        val payload = SetGuildMemberRole(guildId, userId, role.roleId)
        this.send("guild.member.role.unset", payload)
    }

    /**
     * 发送群组消息, 但是使用[MessageChain]
     */
    suspend fun createChannelMessage(channelId: String, content: MessageChain): List<CreateChannelMessage> {
        return this.createChannelMessage(channelId, content.segment)
    }

    /**
     * 发送群组消息, 但是使用纯文本
     */
    suspend fun createChannelMessage(channelId: String, content: String): List<CreateChannelMessage> {
        val payload = CreateChannelMessageOutbound(channelId, content)
        return this.send("message.create", payload).fromArrayJson<List<CreateChannelMessage>>()
    }

    /**
     * 获取群组消息
     */
    suspend fun getChannelMessage(channelId: String, messageId: String): CreateChannelMessage {
        val payload = GetMessageOutbound(channelId, messageId)
        return this.send("message.get", payload).fromJson<CreateChannelMessage>()
    }

    /**
     * 撤回群组消息
     */
    suspend fun deleteChannelMessage(channelId: String, messageId: String) {
        val payload = GetMessageOutbound(channelId, messageId)
        this.send("message.delete", payload)
    }
}