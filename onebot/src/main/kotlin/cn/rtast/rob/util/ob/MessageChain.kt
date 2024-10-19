/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */

@file:Suppress("unused")

package cn.rtast.rob.util.ob

import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.BaseSegment
import cn.rtast.rob.segment.Contact
import cn.rtast.rob.segment.CustomMusicShare
import cn.rtast.rob.segment.DICE
import cn.rtast.rob.segment.Face
import cn.rtast.rob.segment.Image
import cn.rtast.rob.segment.JSON
import cn.rtast.rob.segment.Location
import cn.rtast.rob.segment.MusicShare
import cn.rtast.rob.segment.PlainText
import cn.rtast.rob.segment.Poke
import cn.rtast.rob.segment.RPS
import cn.rtast.rob.segment.Record
import cn.rtast.rob.segment.Reply
import cn.rtast.rob.segment.Shake
import cn.rtast.rob.segment.Share
import cn.rtast.rob.segment.Video
import cn.rtast.rob.segment.XML
import cn.rtast.rob.enums.internal.ContactType
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage

/**
 * 将一个集合([Collection])的[MessageChain.Builder]对象转换成合并转发消息链([NodeMessageChain.Builder])
 * 并且返回未构造的[NodeMessageChain.Builder]对象
 */
fun Collection<MessageChain.Builder>.asNode(senderId: Long): NodeMessageChain.Builder {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it.build(), senderId) }
    return node
}

fun Collection<MessageChain>.asNode(senderId: Long): NodeMessageChain {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it, senderId) }
    return node.build()
}

/**
 * 将一个数组([Array])的[MessageChain.Builder]对象转换成合并转发消息链([NodeMessageChain.Builder])
 * 并且返回已经构造的[NodeMessageChain.Builder]
 */
fun Array<MessageChain.Builder>.asNode(senderId: Long): NodeMessageChain.Builder {
    return this.toList().asNode(senderId)
}

fun Array<MessageChain>.asNode(senderId: Long): NodeMessageChain {
    return this.toList().asNode(senderId)
}

/**
 * 将一个任意类型的集合[Collection]转换为一个构造好的消息链
 * 并且接收一个参数表示是否在每个元素之后插入一个换行符
 * ***注意: 元素必须重写了[toString]方法, 如果一个元素没有重写[toString]方法则会使用这个元素的内存地址***
 */
@JvmOverloads
fun <T> Collection<T?>.asMessageChain(newLine: Boolean = false, filterNull: Boolean = false): MessageChain {
    val msg = MessageChain.Builder()
    if (filterNull) this.filter { it != null }.forEach {
        msg.addText(it.toString())
        if (newLine) msg.addNewLine()
    } else {
        this.forEach {
            msg.addText(it.toString())
            if (newLine) msg.addNewLine()
        }
    }
    return msg.build()
}

/**
 * 将任意类型的数据转换成[MessageChain], 但是最终都会调用这个类型的[toString]方法
 */
fun <T> T?.asMessageChain() = MessageChain.Builder().addText(this.toString()).build()

/**
 * 将任意类型的数据转换成[MessageChain.Builder], 但是最终都会调用这个类型的[toString]方法
 */
fun <T> T?.asMessageChainBuilder() = MessageChain.Builder().addText(this.toString())

/**
 * 将一个任意类型的数组[Array]转换为一个构造完成的消息链
 * 接收一个参数表示是否在每个元素之后插入换行符
 */
@JvmOverloads
fun <T> Array<T>.asMessageChain(newLine: Boolean = false): MessageChain {
    val msg = MessageChain.Builder()
    this.forEach {
        msg.addText(it.toString())
        if (newLine) msg.addNewLine()
    }
    return msg.build()
}

/**
 * 将任意类型的集合[Collection]转换为未构造的消息链构造器
 */
@JvmOverloads
fun <T> Collection<T>.asMessageChainBuilder(newLine: Boolean = false): MessageChain.Builder {
    val msgBuilder = MessageChain.Builder()
    this.forEach {
        msgBuilder.addText(it.toString())
        if (newLine) msgBuilder.addNewLine()
    }
    return msgBuilder
}

/**
 * 将任意类型的数组[Array]转换为未构造的消息连构造器
 */
@JvmOverloads
fun <T> Array<T>.asMessageChainBuilder(newLine: Boolean = false): MessageChain.Builder {
    val msgBuilder = MessageChain.Builder()
    this.forEach {
        msgBuilder.addText(it.toString())
        if (newLine) msgBuilder.addNewLine()
    }
    return msgBuilder
}

/**
 * 快速构造一个数组形式的消息链
 * 支持绝大部分的消息链(Segment)
 */
class MessageChain internal constructor(arrayMessageList: MutableList<BaseSegment>) {

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
        private val arrayMessageList = mutableListOf<BaseSegment>()

        /**
         * 追加一个纯文本消息段
         */
        fun addText(text: String): Builder {
            arrayMessageList.add(PlainText(PlainText.Data(text)))
            return this
        }

        /**
         * 追加一个表情消息段
         */
        fun addFace(id: Int): Builder {
            arrayMessageList.add(Face(Face.Data(id.toString())))
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
                arrayMessageList.add(Image(Image.Data("base64://$rawB64")))
            } else {
                arrayMessageList.add(Image(Image.Data(file)))
            }
            return this
        }

        /**
         * 追加一个语音消息段
         */
        fun addRecord(file: String): Builder {
            arrayMessageList.add(Record(Record.Data(file)))
            return this
        }

        /**
         * 追加一个视频消息段
         */
        fun addVideo(file: String): Builder {
            arrayMessageList.add(Video(Video.Data(file)))
            return this
        }

        /**
         * 追加一个艾特(at)消息段
         */
        fun addAt(qq: Long): Builder {
            arrayMessageList.add(AT(AT.Data(qq.toString())))
            return this
        }

        /**
         * 追加一个猜拳魔法表情消息段
         */
        fun addRPS(): Builder {
            arrayMessageList.add(RPS())
            return this
        }

        /**
         * 追加一个投骰子消息段
         */
        fun addDice(): Builder {
            arrayMessageList.add(DICE())
            return this
        }

        /**
         * 追加一个窗口晃动消息段
         */
        fun addShake(): Builder {
            arrayMessageList.add(Shake())
            return this
        }

        /**
         * 追加一个戳一戳消息段
         */
        fun addPoke(poke: PokeMessage): Builder {
            arrayMessageList.add(Poke(Poke.Data(poke.type.toString(), poke.id.toString())))
            return this
        }

        /**
         * 追加一个链接分享消息段
         * ***在Lagrange.OneBot中并未实现此消息段***
         */
        @JvmOverloads
        fun addShare(url: String, title: String, content: String? = null, image: String? = null): Builder {
            arrayMessageList.add(Share(Share.Data(url, title, content, image)))
            return this
        }

        /**
         * 追加一个分享好友联系人消息段
         */
        fun addContactFriend(id: Long): Builder {
            arrayMessageList.add(Contact(Contact.Data(ContactType.qq, id.toString())))
            return this
        }

        /**
         * 追加一个分享群聊消息段
         */
        fun addContactGroup(id: Long): Builder {
            arrayMessageList.add(Contact(Contact.Data(ContactType.group, id.toString())))
            return this
        }

        /**
         * 追加一个位置分享消息段
         */
        @JvmOverloads
        fun addLocation(lat: Double, lon: Double, title: String? = null, content: String? = null): Builder {
            arrayMessageList.add(Location(Location.Data(lat.toString(), lon.toString(), title, content)))
            return this
        }

        /**
         * 追加一个音乐分享消息段
         */
        fun addMusicShare(type: MusicShareType, id: String): Builder {
            arrayMessageList.add(MusicShare(MusicShare.Data(type.type, id)))
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
            arrayMessageList.add(CustomMusicShare(CustomMusicShare.Data(url, audio, title, content, image)))
            return this
        }

        /**
         * 最佳一个回复(reply)消息段
         */
        fun addReply(id: Long): Builder {
            arrayMessageList.add(Reply(Reply.Data(id.toString())))
            return this
        }

        /**
         * 追加一段XML消息段
         */
        fun addXML(xml: String): Builder {
            arrayMessageList.add(XML(XML.Data(xml)))
            return this
        }

        /**
         * 追加一段Json消息段
         */
        fun addJSON(json: String): Builder {
            arrayMessageList.add(JSON(JSON.Data(json)))
            return this
        }

        /**
         * 追加一行换行符`\n`
         */
        @JvmOverloads
        fun addNewLine(times: Int = 1): Builder {
            repeat(times) {
                arrayMessageList.add(PlainText(PlainText.Data("\n")))
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
        internal fun addRawArrayMessage(content: List<BaseSegment>): Builder {
            arrayMessageList.addAll(content)
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