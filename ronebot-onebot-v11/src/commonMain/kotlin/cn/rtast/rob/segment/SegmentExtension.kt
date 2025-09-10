/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */

@file:Suppress("unused")

package cn.rtast.rob.segment

import cn.rtast.rob.onebot.MessageChain

/**
 * 将一个[Collection] [Segment]转换成[MessageChain]
 */
public fun Collection<Segment>.toMessageChain(): MessageChain = this.toMessageChainBuilder().build()

/**
 * 将单个的[Segment]转换成[MessageChain]
 */
public fun Segment.toMessageChain(): MessageChain = listOf(this).toMessageChainBuilder().build()

/**
 * 将一个[Collection] [Segment]转换成[MessageChain.Builder]
 */
public fun Collection<Segment>.toMessageChainBuilder(): MessageChain.Builder {
    return this.fold(MessageChain.Builder()) { builder, segment ->
        segment.plusMessageChain(builder)
    }
}

/**
 * 将单个的[Segment]转换成[MessageChain.Builder]
 */
public fun Segment.toMessageChainBuilder(): MessageChain.Builder {
    return listOf(this).toMessageChainBuilder()
}

/**
 * 使用[Segment] + 一个[MessageChain]
 */
public operator fun Segment.plus(other: MessageChain): MessageChain {
    return MessageChain.Builder()
        .addSegment(this)
        .addRawArrayMessage(other.finalArrayMsgList)
        .build()
}

/**
 * 内部使用
 * 传入一个[MessageChain.Builder] 将[Segment]追加到[MessageChain.Builder]最后返回
 * [MessageChain.Builder]
 */
internal fun Segment.plusMessageChain(msg: MessageChain.Builder): MessageChain.Builder {
    when (this) {
        is Text -> msg.addText(this.text)
        is Mention -> msg.addMention(this.qq)
        is Face -> msg.addFace(this.id)
        is Image -> msg.addImage(this.resource)
        is Record -> msg.addRecord(this.file)
        is Video -> msg.addVideo(this.file)
        is Poke -> msg.addPoke(this.poke)
        is Reply -> msg.addReply(this.id)
        is XML -> msg.addXML(this.xml)
        is FriendContact -> msg.addContactFriend(this.id)
        is GroupContact -> msg.addContactGroup(this.id)
        is JSON -> msg.addJSON(this.json)
        is MusicShare -> msg.addMusicShare(this.type, this.id)
        is Rps -> msg.addRPS()
        is Dice -> msg.addDice()
        is Shake -> msg.addShake()
        is Share -> msg.addShare(this.url, this.title, this.content, this.image)
        is Location -> msg.addLocation(this.lat, this.lon, this.title, this.content)
        is CustomMusicShare -> msg.addCustomMusicShare(this.url, this.audio, this.title, this.content, this.image)
        is NewLine -> msg.addNewLine(this.times)
        is QFace -> msg.addFace(this.id)
        is Spaces -> msg.addSpaces(this.times)
        is MentionAll -> msg.addMentionAll()
    }
    return msg
}