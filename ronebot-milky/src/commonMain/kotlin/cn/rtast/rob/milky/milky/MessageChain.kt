/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:25 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.milky

import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.entity.Resource
import cn.rtast.rob.milky.enums.ImageSubType
import cn.rtast.rob.milky.segment.*
import kotlin.jvm.JvmOverloads

/**
 * 消息链对象
 */
public class MessageChain internal constructor(
    internal val messageList: MutableList<SendSegment>
) : IMessageChain {
    override val isEmpty: Boolean get() = messageList.isEmpty()
    override val size: Int get() = messageList.size

    public class Builder {
        private val _msgList = mutableListOf<SendSegment>()

        /**
         * 添加纯文本
         */
        public fun addText(text: Any): Builder {
            _msgList.add(STextSegment(STextSegment.Data(text.toString())))
            return this
        }

        /**
         * 添加空白符
         */
        @JvmOverloads
        public fun addSpaces(count: Int = 1): Builder =
            this.addText(" ".repeat(count))

        /**
         * 添加换行
         */
        @JvmOverloads
        public fun addNewLine(count: Int = 1): Builder =
            this.addText("\n".repeat(count))

        /**
         * 添加@
         */
        public fun addAt(userId: Long): Builder {
            _msgList.add(SAtSegment(SAtSegment.Data(userId)))
            return this
        }

        /**
         * 添加回复
         */
        public fun addReply(messageSeq: Long): Builder {
            _msgList.add(SReplySegment(SReplySegment.Data(messageSeq)))
            return this
        }

        /**
         * 添加表情
         */
        public fun addFace(faceId: String): Builder {
            _msgList.add(SFaceSegment(SFaceSegment.Data(faceId)))
            return this
        }

        /**
         * 添加图片
         */
        @JvmOverloads
        public fun addImage(
            uri: String,
            subType: ImageSubType = ImageSubType.normal,
            summary: String? = null
        ): Builder {
            _msgList.add(SImageSegment(SImageSegment.Data(uri, subType, summary)))
            return this
        }

        /**
         * 添加图片但是使用[Resource]对象
         */
        public fun addImage(
            resource: Resource,
            subType: ImageSubType = ImageSubType.normal,
            summary: String? = null
        ): Builder = this.addImage(resource.toString(), subType, summary)

        /**
         * 添加语音
         */
        public fun addRecord(uri: String): Builder {
            _msgList.add(SRecordSegment(SRecordSegment.Data(uri)))
            return this
        }

        /**
         * 添加语音但是使用[Resource]对象
         */
        public fun addRecord(resource: Resource): Builder = this.addRecord(resource.toString())

        /**
         * 添加视频
         */
        public fun addVideo(uri: String, thumbUri: String? = null): Builder {
            _msgList.add(SVideoSegment(SVideoSegment.Data(uri, thumbUri)))
            return this
        }

        /**
         * 添加视频但是用[Resource]对象
         */
        public fun addVideo(uriResource: Resource, thumbResource: Resource): Builder =
            this.addVideo(uriResource.toString(), thumbResource.toString())

        /**
         * 添加合并转发
         */
        public fun addForward() {
            TODO("TODO TODO TODO TODO")
        }

        public fun build(): MessageChain = MessageChain(_msgList)
    }
}