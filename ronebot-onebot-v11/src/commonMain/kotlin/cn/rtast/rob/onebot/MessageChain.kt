/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */

@file:Suppress("unused", "KDocUnresolvedReference", "DEPRECATION")

package cn.rtast.rob.onebot

import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.entity.Resource
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.enums.internal.ContactType
import cn.rtast.rob.onebot.dsl.MessageChainDsl
import cn.rtast.rob.segment.*
import kotlin.jvm.JvmOverloads

/**
 * 快速构造一个数组形式的消息链
 * 支持绝大部分的消息链(Segment)
 */
@MessageChainDsl
public class MessageChain internal constructor(arrayMessageList: MutableList<InternalBaseSegment>) : IMessageChain {

    internal val finalArrayMsgList = arrayMessageList

    /**
     * 将一个构造好的消息链([MessageChain])转换为Node([NodeMessageChain])
     * 并提供一个发送者ID
     */
    public fun asNode(userId: Long): NodeMessageChain {
        val node = NodeMessageChain.Builder()
            .addMessageChain(this, userId).build()
        return node
    }

    override fun toString(): String {
        return "MessageChain{${finalArrayMsgList.joinToString()}}"
    }

    /**
     * 对一个[MessageChain]对象使用+操作符拼接[Segment]
     * 这个操作符的使用是为了连接 + 方法
     */
    public operator fun plus(segment: Segment): MessageChain {
        return Builder()
            .addRawArrayMessage(this.finalArrayMsgList)
            .addSegment(segment)
            .build()
    }

    /**
     * 使两个[MessageChain]对象可以快速拼接起来合并成一个
     * 完整的[MessageChain]
     */
    @Deprecated("Use chain builder instead", level = DeprecationLevel.HIDDEN)
    public operator fun MessageChain.plus(other: MessageChain): MessageChain {
        this.finalArrayMsgList.addAll(other.finalArrayMsgList)
        return this.finalArrayMsgList.toMessageChainInternal()
    }

    /**
     * 使两个[MessageChain.Builder]对象可以快速拼接起来合并成一个
     * 完整的[MessageChain.Builder]
     */
    @Deprecated("Use chain builder instead", level = DeprecationLevel.HIDDEN)
    public operator fun Builder.plus(other: Builder): Builder {
        this.addRawArrayMessage(other.arrayMessageList)
        return this.arrayMessageList.toMessageChainBuilderInternal()
    }

    public override val isEmpty: Boolean get() = finalArrayMsgList.isEmpty()

    public override val size: Int get() = finalArrayMsgList.size

    @MessageChainDsl
    public class Builder {
        internal val arrayMessageList = mutableListOf<InternalBaseSegment>()

        /**
         * 追加一个纯文本消息段
         */
        public fun addText(text: Any): Builder {
            arrayMessageList.add(IPlainText(IPlainText.Data(text.toString())))
            return this
        }

        /**
         * 追加一个纯文本并在末尾上附带一个换行
         */
        public fun addTextLine(text: Any): Builder {
            return addText("$text\n")
        }

        /**
         * 追加一个表情消息段, 但是类型是[QQFace]
         */
        public fun addFace(face: QQFace): Builder {
            arrayMessageList.add(IFace(IFace.Data(face.id.toString())))
            return this
        }

        /**
         * 追加一个表情消息段, 但是类型是一个整形
         */
        public fun addFace(face: Int): Builder {
            arrayMessageList.add(IFace(IFace.Data(face.toString())))
            return this
        }

        /**
         * 追加一个图片消息段, 并且可以指定是否以base64字符串形式发送
         * 如果不是base64字符串, 请提供一个可访问的图片URL
         */
        @JvmOverloads
        @Deprecated("该API已弃用, 请使用Resource对象传入")
        public fun addImage(file: String, base64: Boolean = false): Builder {
            if (base64) {
                val rawB64 = file.replace("data:image/png;base64,", "")
                arrayMessageList.add(IImage(IImage.Data("base64://$rawB64")))
            } else {
                arrayMessageList.add(IImage(IImage.Data(file)))
            }
            return this
        }

        /**
         * 追加图片但是使用[Resource]对象
         */
        public fun addImage(resource: Resource): Builder {
            if (resource.base64) {
                val rawB64 = resource.content.replace("data:image/png;base64,", "")
                arrayMessageList.add(IImage(IImage.Data("base64://$rawB64")))
            } else {
                arrayMessageList.add(IImage(IImage.Data(resource.content)))
            }
            return this
        }

        /**
         * 追加一个语音消息段
         */
        public fun addRecord(file: String): Builder {
            arrayMessageList.add(IRecord(IRecord.Data(file)))
            return this
        }

        /**
         * 追加一个视频消息段
         */
        public fun addVideo(file: String): Builder {
            arrayMessageList.add(IVideo(IVideo.Data(file)))
            return this
        }

        /**
         * 追加一个艾特(at)消息段
         */
        @Deprecated("该方法已弃用, 请使用mention方法")
        public fun addAt(qq: Long): Builder {
            arrayMessageList.add(IAT(IAT.Data(qq.toString())))
            return this
        }

        /**
         * 添加一个@(提及)
         */
        public fun addMention(qq: Long): Builder = this.addAt(qq)

        /**
         * 追加一个猜拳魔法表情消息段
         */
        public fun addRPS(): Builder {
            arrayMessageList.add(IRps())
            return this
        }

        /**
         * 追加一个投骰子消息段
         */
        public fun addDice(): Builder {
            arrayMessageList.add(IDice())
            return this
        }

        /**
         * 追加一个窗口晃动消息段
         */
        public fun addShake(): Builder {
            arrayMessageList.add(IShake())
            return this
        }

        /**
         * 追加一个戳一戳消息段
         * 这个戳一戳是旧版的戳一戳和新版的戳一戳的触发方式不同,
         * 新版的触发方式是快速双击头像进行戳一戳, 旧版的戳一戳
         * 现在已被移除无法发送, 只有在旧版QQ客户端才能触发
         */
        public fun addPoke(poke: PokeMessage): Builder {
            arrayMessageList.add(IPoke(IPoke.Data(poke.type.toString(), poke.id.toString())))
            return this
        }

        /**
         * 追加一个链接分享消息段
         * ***在Lagrange.OneBot中并未实现此消息段***
         */
        @JvmOverloads
        public fun addShare(url: String, title: String, content: String? = null, image: String? = null): Builder {
            arrayMessageList.add(IShare(IShare.Data(url, title, content, image)))
            return this
        }

        /**
         * 追加一个分享好友联系人消息段
         */
        public fun addContactFriend(userId: Long): Builder {
            arrayMessageList.add(IContact(IContact.Data(ContactType.qq, userId.toString())))
            return this
        }

        /**
         * 追加一个分享群聊消息段
         */
        public fun addContactGroup(groupId: Long): Builder {
            arrayMessageList.add(IContact(IContact.Data(ContactType.group, groupId.toString())))
            return this
        }

        /**
         * 追加一个位置分享消息段
         */
        @JvmOverloads
        public fun addLocation(lat: Double, lon: Double, title: String? = null, content: String? = null): Builder {
            arrayMessageList.add(ILocation(ILocation.Data(lat.toString(), lon.toString(), title, content)))
            return this
        }

        /**
         * 追加一个音乐分享消息段
         */
        public fun addMusicShare(type: MusicShareType, id: String): Builder {
            arrayMessageList.add(IMusicShare(IMusicShare.Data(type.type, id)))
            return this
        }

        /**
         * 追加一段自定义音乐分享消息段
         */
        @JvmOverloads
        public fun addCustomMusicShare(
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
        public fun addReply(id: Long): Builder {
            arrayMessageList.add(IReply(IReply.Data(id.toString())))
            return this
        }

        /**
         * 追加一段XML消息段
         */
        public fun addXML(xml: String): Builder {
            arrayMessageList.add(IXml(IXml.Data(xml)))
            return this
        }

        /**
         * 追加一段Json消息段
         */
        public fun addJSON(json: String): Builder {
            arrayMessageList.add(IJson(IJson.Data(json)))
            return this
        }

        /**
         * 追加一行换行符`\n`
         */
        @JvmOverloads
        public fun addNewLine(times: Int = 1): Builder {
            repeat(times) {
                arrayMessageList.add(IPlainText(IPlainText.Data("\n")))
            }
            return this
        }

        /**
         * 追加一个未构造的消息链构造器([MessageChain.Builder])
         */
        public fun addMessageChain(content: Builder): Builder {
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
        public fun addSegment(segment: Segment): Builder {
            return segment.plusMessageChain(this)
        }

        /**
         * 追加一个@全体成员的消息段
         */
        @Deprecated("该方法已弃用, 请使用mentionAll方法")
        public fun addAtAll(): Builder {
            arrayMessageList.add(IAT(IAT.Data("all")))
            return this
        }

        /**
         * 添加@全体(提及全体成员)
         */
        public fun addMentionAll(): Builder = this.addAtAll()

        /**
         * 追加指定数量的空格
         * @param times 1
         */
        @JvmOverloads
        public fun addSpaces(times: Int = 1): Builder {
            val spaces = buildString {
                repeat(times) {
                    append(" ")
                }
            }
            arrayMessageList.add(IPlainText(IPlainText.Data(spaces)))
            return this
        }

        /**
         * 添加Markdown
         */
        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Can't be used")
        public fun addMarkdown(content: String): Builder {
            arrayMessageList.add(IMarkdown(IMarkdown.Data(content)))
            return this
        }

        /**
         * 用于dsl的方式添加[Segment], 使用下面的方式调用
         * ```kotlin
         *  messageChain {
         *     +Text("22222")
         *     +Image("https://a.com/a.png")
         * }
         * ```
         */
        public operator fun Segment.unaryPlus()= addSegment(this)

        /**
         * +一个字符串
         */
        public operator fun String.unaryPlus() = addText(this)

        /**
         * 将文本反转
         */
        public operator fun Text.not() = addText(this.text.toString().reversed())

        /**
         * 将文本反转
         */
        public operator fun String.not() = addText(this.reversed())

        /**
         * 添加任意的[Segment]
         */
        public fun add(segment: Segment): Builder = this.addSegment(segment)

        /**
         * 用于dsl的方式添加[Segment]
         * 使用下面两种方式来调用
         * ```kotlin
         *     this(Text("test1"))
         *     invoke(Image("https://example.com/example.png"))
         * ```
         */
        public operator fun invoke(segment: Segment): Builder = this.addSegment(segment)

        public fun build(): MessageChain {
            return MessageChain(arrayMessageList)
        }

        override fun toString(): String {
            return "MessageChain.Builder{${arrayMessageList.joinToString()}}"
        }
    }
}