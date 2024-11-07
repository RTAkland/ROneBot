/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */

@file:Suppress("unused", "KDocUnresolvedReference")

package cn.rtast.rob.util.ob

import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.enums.internal.ContactType
import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.CustomMusicShare
import cn.rtast.rob.segment.Dice
import cn.rtast.rob.segment.Face
import cn.rtast.rob.segment.FriendContact
import cn.rtast.rob.segment.GroupContact
import cn.rtast.rob.segment.IAT
import cn.rtast.rob.segment.IContact
import cn.rtast.rob.segment.ICustomMusicShare
import cn.rtast.rob.segment.IDice
import cn.rtast.rob.segment.IFace
import cn.rtast.rob.segment.IImage
import cn.rtast.rob.segment.IJson
import cn.rtast.rob.segment.ILocation
import cn.rtast.rob.segment.IMusicShare
import cn.rtast.rob.segment.IPlainText
import cn.rtast.rob.segment.IPoke
import cn.rtast.rob.segment.IRecord
import cn.rtast.rob.segment.IReply
import cn.rtast.rob.segment.IRps
import cn.rtast.rob.segment.IShake
import cn.rtast.rob.segment.IShare
import cn.rtast.rob.segment.IVideo
import cn.rtast.rob.segment.IXml
import cn.rtast.rob.segment.Image
import cn.rtast.rob.segment.InternalBaseSegment
import cn.rtast.rob.segment.JSON
import cn.rtast.rob.segment.Location
import cn.rtast.rob.segment.MusicShare
import cn.rtast.rob.segment.NewLine
import cn.rtast.rob.segment.Poke
import cn.rtast.rob.segment.QFace
import cn.rtast.rob.segment.Record
import cn.rtast.rob.segment.Reply
import cn.rtast.rob.segment.Rps
import cn.rtast.rob.segment.Segment
import cn.rtast.rob.segment.Shake
import cn.rtast.rob.segment.Share
import cn.rtast.rob.segment.Text
import cn.rtast.rob.segment.Video
import cn.rtast.rob.segment.XML

/**
 * 快速构造一个数组形式的消息链
 * 支持绝大部分的消息链(Segment)
 */
class MessageChain internal constructor(arrayMessageList: MutableList<InternalBaseSegment>) {

    internal val finalArrayMsgList = arrayMessageList

    /**
     * 将一个构造好的消息链([MessageChain])转换为Node([NodeMessageChain])
     * 并提供一个发送者ID
     */
    fun asNode(userId: Long): NodeMessageChain {
        val node = NodeMessageChain.Builder()
            .addMessageChain(this, userId).build()
        return node
    }

    override fun toString(): String {
        return "MessageChain{${finalArrayMsgList.joinToString()}}"
    }

    class Builder {
        internal val arrayMessageList = mutableListOf<InternalBaseSegment>()

        /**
         * 追加一个纯文本消息段
         */
        fun addText(text: String): Builder {
            arrayMessageList.add(IPlainText(IPlainText.Data(text)))
            return this
        }

        /**
         * 追加一个表情消息段, 但是类型是[QQFace]
         */
        fun addFace(face: QQFace): Builder {
            arrayMessageList.add(IFace(IFace.Data(face.id.toString())))
            return this
        }

        /**
         * 追加一个表情消息段, 但是类型是一个整形
         */
        fun addFace(face: Int): Builder {
            arrayMessageList.add(IFace(IFace.Data(face.toString())))
            return this
        }

        /**
         * 追加一个图片消息段, 并且可以指定是否以base64字符串形式发送
         * 如果不是base64字符串, 请提供一个可访问的图片URL
         */
        @JvmOverloads
        fun addImage(file: String, base64: Boolean = false): Builder {
            if (base64) {
                val rawB64 = file.replace("data:image/png;base64,", "")
                arrayMessageList.add(IImage(IImage.Data("base64://$rawB64")))
            } else {
                arrayMessageList.add(IImage(IImage.Data(file)))
            }
            return this
        }

        /**
         * 追加一个语音消息段
         */
        fun addRecord(file: String): Builder {
            arrayMessageList.add(IRecord(IRecord.Data(file)))
            return this
        }

        /**
         * 追加一个视频消息段
         */
        fun addVideo(file: String): Builder {
            arrayMessageList.add(IVideo(IVideo.Data(file)))
            return this
        }

        /**
         * 追加一个艾特(at)消息段
         */
        fun addAt(qq: Long): Builder {
            arrayMessageList.add(IAT(IAT.Data(qq.toString())))
            return this
        }

        /**
         * 追加一个猜拳魔法表情消息段
         */
        fun addRPS(): Builder {
            arrayMessageList.add(IRps())
            return this
        }

        /**
         * 追加一个投骰子消息段
         */
        fun addDice(): Builder {
            arrayMessageList.add(IDice())
            return this
        }

        /**
         * 追加一个窗口晃动消息段
         */
        fun addShake(): Builder {
            arrayMessageList.add(IShake())
            return this
        }

        /**
         * 追加一个戳一戳消息段
         * 这个戳一戳是旧版的戳一戳和新版的戳一戳的触发方式不同,
         * 新版的触发方式是快速双击头像进行戳一戳, 旧版的戳一戳
         * 现在已被移除无法发送, 只有在旧版QQ客户端才能触发
         */
        fun addPoke(poke: PokeMessage): Builder {
            arrayMessageList.add(IPoke(IPoke.Data(poke.type.toString(), poke.id.toString())))
            return this
        }

        /**
         * 追加一个链接分享消息段
         * ***在Lagrange.OneBot中并未实现此消息段***
         */
        @JvmOverloads
        fun addShare(url: String, title: String, content: String? = null, image: String? = null): Builder {
            arrayMessageList.add(IShare(IShare.Data(url, title, content, image)))
            return this
        }

        /**
         * 追加一个分享好友联系人消息段
         */
        fun addContactFriend(userId: Long): Builder {
            arrayMessageList.add(IContact(IContact.Data(ContactType.qq, userId.toString())))
            return this
        }

        /**
         * 追加一个分享群聊消息段
         */
        fun addContactGroup(groupId: Long): Builder {
            arrayMessageList.add(IContact(IContact.Data(ContactType.group, groupId.toString())))
            return this
        }

        /**
         * 追加一个位置分享消息段
         */
        @JvmOverloads
        fun addLocation(lat: Double, lon: Double, title: String? = null, content: String? = null): Builder {
            arrayMessageList.add(ILocation(ILocation.Data(lat.toString(), lon.toString(), title, content)))
            return this
        }

        /**
         * 追加一个音乐分享消息段
         */
        fun addMusicShare(type: MusicShareType, id: String): Builder {
            arrayMessageList.add(IMusicShare(IMusicShare.Data(type.type, id)))
            return this
        }

        /**
         * 追加一段自定义音乐分享消息段
         */
        fun addCustomMusicShare(
            url: String,
            audio: String,
            title: String,
            content: String? = null,
            image: String? = null
        ): Builder {
            arrayMessageList.add(ICustomMusicShare(ICustomMusicShare.Data(url, audio, title, content, image)))
            return this
        }

        /**
         * 最佳一个回复(reply)消息段
         */
        fun addReply(id: Long): Builder {
            arrayMessageList.add(IReply(IReply.Data(id.toString())))
            return this
        }

        /**
         * 追加一段XML消息段
         */
        fun addXML(xml: String): Builder {
            arrayMessageList.add(IXml(IXml.Data(xml)))
            return this
        }

        /**
         * 追加一段Json消息段
         */
        fun addJSON(json: String): Builder {
            arrayMessageList.add(IJson(IJson.Data(json)))
            return this
        }

        /**
         * 追加一行换行符`\n`
         */
        @JvmOverloads
        fun addNewLine(times: Int = 1): Builder {
            repeat(times) {
                arrayMessageList.add(IPlainText(IPlainText.Data("\n")))
            }
            return this
        }

        /**
         * 追加一个未构造的消息链构造器([MessageChain.Builder])
         */
        fun addMessageChain(content: Builder): Builder {
            arrayMessageList.addAll(content.arrayMessageList)
            return this
        }

        /**
         * 追加服务端下发的数组形式消息段
         * ***仅限内部使用***
         */
        internal fun addRawArrayMessage(content: List<InternalBaseSegment>): Builder {
            arrayMessageList.addAll(content)
            return this
        }

        /**
         * 追加一个[Segment], 使用when+隐式类型转换实现追加
         */
        fun addSegment(segment: Segment): Builder {
            when (segment) {
                is Text -> addText(segment.text)
                is AT -> addAt(segment.qq)
                is Face -> addFace(segment.id)
                is Image -> addImage(segment.file)
                is Record -> addRecord(segment.file)
                is Video -> addVideo(segment.file)
                is Poke -> addPoke(segment.poke)
                is Reply -> addReply(segment.id.toLong())
                is XML -> addXML(segment.xml)
                is FriendContact -> addContactFriend(segment.id.toLong())
                is GroupContact -> addContactGroup(segment.id.toLong())
                is JSON -> addJSON(segment.json)
                is MusicShare -> addMusicShare(segment.type, segment.id)
                is Rps -> addRPS()
                is Dice -> addDice()
                is Shake -> addShake()
                is Share -> addShare(segment.url, segment.title, segment.content, segment.image)
                is NewLine -> addNewLine(segment.times)
                is QFace -> addFace(segment.id)
                is Location -> addLocation(
                    segment.lat.toDouble(),
                    segment.lon.toDouble(),
                    segment.title,
                    segment.content
                )

                is CustomMusicShare -> addCustomMusicShare(
                    segment.url,
                    segment.audio,
                    segment.title,
                    segment.content,
                    segment.image
                )
            }
            return this
        }

        fun build(): MessageChain {
            return MessageChain(arrayMessageList)
        }

        override fun toString(): String {
            return "MessageChain.Builder{${arrayMessageList.joinToString()}}"
        }
    }
}