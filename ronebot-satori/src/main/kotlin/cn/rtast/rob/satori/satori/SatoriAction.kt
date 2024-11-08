/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.satori

import cn.rtast.rob.satori.BotInstance
import cn.rtast.rob.satori.entity.guild.inbound.GetGuild
import cn.rtast.rob.satori.entity.guild.inbound.GetGuildList
import cn.rtast.rob.satori.entity.guild.inbound.GetGuildMember
import cn.rtast.rob.satori.entity.guild.inbound.GetGuildMemberList
import cn.rtast.rob.satori.entity.guild.inbound.GetGuildRole
import cn.rtast.rob.satori.entity.guild.outbound.ApproveGuildRequestOutbound
import cn.rtast.rob.satori.entity.guild.outbound.GetGuildMemberListOutbound
import cn.rtast.rob.satori.entity.guild.outbound.GetGuildMemberOutbound
import cn.rtast.rob.satori.entity.guild.outbound.GetGuildOutbound
import cn.rtast.rob.satori.entity.guild.outbound.GetGuildRoleOutbound
import cn.rtast.rob.satori.entity.guild.outbound.KickGuildMemberOutbound
import cn.rtast.rob.satori.entity.guild.outbound.MuteGuildMemberOutbound
import cn.rtast.rob.satori.entity.guild.outbound.SetGuildMemberRole
import cn.rtast.rob.satori.enums.GuildUserRole
import cn.rtast.rob.satori.util.Http
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import kotlin.time.Duration

class SatoriAction internal constructor(
    private val botInstance: BotInstance,
) : ExtAction {
    override suspend fun send(api: String, payload: String?): String {
        val newPayload = payload ?: "{}"
        return Http.post(
            "${botInstance.apiAddress}/v1/$api", newPayload, mapOf(
                "Authorization" to "Bearer ${botInstance.apiAccessToken}",
                "Satori-User-ID" to botInstance.botUserId,
                "Satori-Platform" to botInstance.botPlatforms.platformName
            )
        )
    }

    suspend fun getGuild(guildId: String): GetGuild {
        val payload = GetGuildOutbound(guildId).toJson()
        return this.send("guild.get", payload).fromJson<GetGuild>()
    }

    suspend fun getGuildList(): List<GetGuild> {
        return this.send("guild.list", "").fromJson<GetGuildList>().data
    }

    suspend fun approveGuildRequest(id: String, approve: Boolean = true, comment: String? = null) {
        val payload = ApproveGuildRequestOutbound(id, approve, comment).toJson()
        this.send("guild.approve", payload)
    }

    suspend fun getGuildMember(guildId: String, userId: String): GetGuildMember {
        val payload = GetGuildMemberOutbound(guildId, userId).toJson()
        return this.send("guild.member.get", payload).fromJson<GetGuildMember>()
    }

    suspend fun getGuildMemberList(guildId: String): List<GetGuildMember> {
        val payload = GetGuildMemberListOutbound(guildId).toJson()
        return this.send("guild.member.list", payload).fromJson<GetGuildMemberList>().data
    }

    suspend fun kickGuildMember(guildId: String, userId: String, permanent: Boolean = false) {
        val payload = KickGuildMemberOutbound(guildId, userId, permanent).toJson()
        this.send("guild.member.kick", payload)
    }

    suspend fun muteGuildMember(guildId: String, userId: String, duration: Duration) {
        val payload = MuteGuildMemberOutbound(guildId, userId, duration.inWholeMilliseconds).toJson()
        this.send("guild.member.mute", payload)
    }

    suspend fun approveInvite(id: String, approve: Boolean, comment: String? = null) {
        val payload = ApproveGuildRequestOutbound(id, approve, comment).toJson()
        this.send("guild.member.approve", payload)
    }

    suspend fun getGuildRole(guildId: String): List<GetGuildRole.Role> {
        val payload = GetGuildRoleOutbound(guildId).toJson()
        return this.send("guild.role.list", payload).fromJson<GetGuildRole>().data
    }

    suspend fun setGuildMemberRole(guildId: String, userId: String, role: GuildUserRole) {
        val payload = SetGuildMemberRole(guildId, userId, role.roleId).toJson()
        this.send("guild.member.role.set", payload)
    }

    suspend fun unsetGuildMemberRole(guildId: String, userId: String, role: GuildUserRole) {
        val payload = SetGuildMemberRole(guildId, userId, role.roleId).toJson()
        this.send("guild.member.role.unset", payload)
    }
}