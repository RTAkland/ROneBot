/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/31
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

/**
 * 音乐分享的类别
 */
enum class MusicShareType(val type: String) {
    QQ("qq"), Netease("163"), Xiami("xm");

    companion object {
        fun forName(name: String): MusicShareType {
            return when (name) {
                QQ.name -> QQ
                Netease.name -> Netease
                Xiami.name -> Xiami
                else -> QQ
            }
        }
    }
}