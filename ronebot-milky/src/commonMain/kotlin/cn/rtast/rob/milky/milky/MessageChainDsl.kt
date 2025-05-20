/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:39 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.milky

import cn.rtast.rob.annotations.MessageChainDsl
import cn.rtast.rob.entity.Resource
import cn.rtast.rob.milky.enums.ImageSubType

/**
 * 创建一个新的消息链对象
 */
public inline fun messageChain(builder: MessageChain.Builder.() -> Unit): MessageChain =
    MessageChain.Builder().apply(builder).build()

/**
 * 根上面一样, 但是更简短
 */
public inline fun message(builder: MessageChain.Builder.() -> Unit): MessageChain =
    messageChain(builder)

@Deprecated(message = "消息链内不允许嵌套消息链", level = DeprecationLevel.ERROR)
public inline fun MessageChain.Builder.messageChain(builder: (@MessageChainDsl MessageChain.Builder).() -> Unit) = this

@Deprecated(message = "消息链内不允许嵌套消息链", level = DeprecationLevel.ERROR)
public inline fun MessageChain.Builder.message(builder: (@MessageChainDsl MessageChain.Builder).() -> Unit) = this

/**
 * 追加文本
 */
public fun MessageChain.Builder.text(text: Any): MessageChain.Builder = this.addText(text)

/**
 * 追加空格
 */
public fun MessageChain.Builder.spaces(count: Int = 1): MessageChain.Builder = this.addSpaces(count)

/**
 * 追加换行
 */
public fun MessageChain.Builder.newline(count: Int = 1): MessageChain.Builder = this.addNewLine(count)

/**
 * 追加@
 */
public fun MessageChain.Builder.at(userId: Long): MessageChain.Builder = this.addAt(userId)

/**
 * 追加回复
 */
public fun MessageChain.Builder.reply(messageSeq: Long): MessageChain.Builder = this.addReply(messageSeq)

/**
 * 追加表情
 */
public fun MessageChain.Builder.face(faceId: String): MessageChain.Builder = this.addFace(faceId)

/**
 * 追加图片
 */
public fun MessageChain.Builder.image(
    uri: String,
    subType: ImageSubType = ImageSubType.Normal,
    summary: String? = null
): MessageChain.Builder = this.addImage(uri, subType, summary)

/**
 * 追加图片但是使用[Resource]对象
 */
public fun MessageChain.Builder.image(
    resource: Resource,
    subType: ImageSubType = ImageSubType.Normal,
    summary: String? = null
): MessageChain.Builder = this.addImage(resource, subType, summary)

/**
 * 追加语音
 */
public fun MessageChain.Builder.record(uri: String): MessageChain.Builder = this.addRecord(uri)

/**
 * 追加语音但是使用[Resource]对象
 */
public fun MessageChain.Builder.record(resource: Resource): MessageChain.Builder = this.addRecord(resource)

/**
 * 追加视频
 */
public fun MessageChain.Builder.video(uri: String, thumbUri: String? = null): MessageChain.Builder =
    this.addVideo(uri, thumbUri)

/**
 * 追加视频但是使用[Resource]对象
 */
public fun MessageChain.Builder.video(uriResource: Resource, thumbResource: Resource): MessageChain.Builder =
    this.addVideo(uriResource, thumbResource)