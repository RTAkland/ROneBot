/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

/**
 * 表示一个Honor的类型具体是哪种类型会在下方标注出
 */
enum class HonorType(val type: String) {
    All("all"), Performer("performer"),
    Legend("legend"), StrongNewBie("strong_newbie"),
    Emotion("emotion");

    companion object {
        fun forName(name: String): HonorType? {
            return HonorType.entries.find { it.name == name }
        }
    }
}