/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/31
 */

@file:Suppress("unused", "DEPRECATION")

package cn.rtast.rob.onebot

import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage
import cn.rtast.rob.enums.QQFace

@Deprecated(
    "CQ Code message chain is not support now, use MessageChain instead",
    replaceWith = ReplaceWith("MessageChain"),
    level = DeprecationLevel.WARNING
)
class CQMessageChain internal constructor(builder: StringBuilder) {

    val finalString = builder.toString()

    @Deprecated(
        "CQ Code message chain is not support now, use MessageChain instead",
        replaceWith = ReplaceWith("MessageChain.Builder")
    )
    class Builder {
        private val stringBuilder = StringBuilder()

        fun addAt(userId: Long): Builder {
            stringBuilder.append("[CQ:at,qq=$userId]")
            return this
        }

        fun addText(text: String): Builder {
            stringBuilder.append(text)
            return this
        }

        fun addImage(file: String): Builder {
            stringBuilder.append("[CQ:image,file=$file]")
            return this
        }

        fun addFace(face: QQFace): Builder {
            stringBuilder.append("[CQ:face,id=${face.id}]")
            return this
        }

        fun addFace(face: Int): Builder {
            stringBuilder.append("[CQ:face,id=${face}]")
            return this
        }

        fun addRecord(file: String): Builder {
            stringBuilder.append("[CQ:record,file=$file]")
            return this
        }

        fun addVideo(file: String): Builder {
            stringBuilder.append("[CQ:video,file=$file]")
            return this
        }

        fun addRPS(): Builder {
            stringBuilder.append("[CQ:rps]")
            return this
        }

        fun addDice(): Builder {
            stringBuilder.append("[CQ:dice]")
            return this
        }

        fun addShake(): Builder {
            stringBuilder.append("[CQ:shake]")
            return this
        }

        fun addPoke(poke: PokeMessage): Builder {
            stringBuilder.append("[CQ:poke,type=${poke.type},id=${poke.id}]")
            return this
        }

        fun addShare(url: String, title: String): Builder {
            stringBuilder.append("[CQ:share,url=$url,title=$title]")
            return this
        }

        fun addContactUser(userId: Long): Builder {
            stringBuilder.append("[CQ:contact,type=qq,id=$userId]")
            return this
        }

        fun addContactGroup(groupId: Long): Builder {
            stringBuilder.append("[CQ:contact,type=group,id=$groupId]")
            return this
        }

        @JvmOverloads
        fun addLocation(lat: Double, lon: Double, title: String? = null, content: String? = null): Builder {
            stringBuilder.append("[CQ:location,lat=${lat},lon=${lon}")
            if (title != null) stringBuilder.append(",title=$title")
            if (content != null) stringBuilder.append(",content=$content")
            stringBuilder.append("]")
            return this
        }

        fun addMusicShare(type: MusicShareType, id: String): Builder {
            stringBuilder.append("[CQ:music,type=${type.type},id=$id]")
            return this
        }

        @JvmOverloads
        fun addCustomMusicShare(
            url: String,
            audio: String,
            title: String,
            content: String? = null,
            image: String? = null
        ): Builder {
            stringBuilder.append("[CQ:music,type=custom,url=$url,audio=$audio,title=$title")
            if (image != null) stringBuilder.append(",image=$image")
            if (content != null) stringBuilder.append(",content=$content")
            stringBuilder.append("]")
            return this
        }

        fun addReply(messageId: Long): Builder {
            stringBuilder.append("[CQ:reply,id=$messageId]")
            return this
        }

        fun addForwardMessage(messageId: String): Builder {
            stringBuilder.append("[CQ:forward,id=$messageId]")
            return this
        }

        fun addForwardNodeMessage(messageId: String): Builder {
            stringBuilder.append("[CQ:node,id=$messageId]")
            return this
        }

        fun addXMLMessage(xml: String): Builder {
            stringBuilder.append("[CQ:xml,data=$xml]")
            return this
        }

        fun addJsonMessage(json: String): Builder {
            stringBuilder.append("[CQ:json,data=$json]")
            return this
        }

        @JvmOverloads
        fun addNewLine(repeatTimes: Int = 1): Builder {
            repeat(repeatTimes) {
                stringBuilder.append("\n")
            }
            return this
        }

        fun build(): CQMessageChain {
            return CQMessageChain(stringBuilder)
        }
    }
}