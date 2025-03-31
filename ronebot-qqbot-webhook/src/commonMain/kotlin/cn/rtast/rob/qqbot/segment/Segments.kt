/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.segment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Markdown(val content: String)


@Serializable
public data class Keyboard(val content: List<Row>) {
    @Serializable
    public data class Row(val buttons: List<Button>)

    @Serializable
    public data class Button(
        val id: String,
        @SerialName("render_data")
        val renderData: RenderData,
        val action: Action,
    )

    @Serializable
    public data class RenderData(
        val label: String,
        @SerialName("visited_label")
        val visitedLabel: String,
        val style: Int
    )

    @Serializable
    public data class Action(
        val type: String,
        val permission: Permission,
        val data: String,
        val reply: Boolean,
        val enter: Boolean,
        val anchor: Int,
        @SerialName("unsupport_tips")
        val unsupportedTips: String,
    )

    @Serializable
    public data class Permission(
        val type: Int,
        @SerialName("specify_user_ids")
        val specifyUserIds: List<Long>,
        @SerialName("specify_role_ids")
        val specifyRoleIds: List<Long>,
    )
}