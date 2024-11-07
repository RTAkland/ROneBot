/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/7
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.CustomMusicShare
import cn.rtast.rob.segment.Dice
import cn.rtast.rob.segment.Face
import cn.rtast.rob.segment.FriendContact
import cn.rtast.rob.segment.GroupContact
import cn.rtast.rob.segment.IPlainText
import cn.rtast.rob.segment.Image
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
 * 将一个集合([Collection])的[MessageChain.Builder]对象转换成合并转发消息链([NodeMessageChain.Builder])
 * 并且返回未构造的[NodeMessageChain.Builder]对象
 */
fun Collection<MessageChain.Builder>.toNode(senderId: Long): NodeMessageChain.Builder {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it.build(), senderId) }
    return node
}

/**
 * 将一个集合([Collection])的[MessageChain]对象转换成合并转发消息链([NodeMessageChain])
 * 并且返回未构造的[NodeMessageChain]对象
 */
fun Collection<MessageChain>.toNode(senderId: Long): NodeMessageChain {
    val node = NodeMessageChain.Builder()
    this.forEach { node.addMessageChain(it, senderId) }
    return node.build()
}

/**
 * 将一个数组([Array])的[MessageChain.Builder]对象转换成合并转发消息链([NodeMessageChain.Builder])
 * 并且返回已经构造的[NodeMessageChain.Builder]
 */
fun Array<MessageChain.Builder>.toNode(senderId: Long): NodeMessageChain.Builder {
    return this.toList().toNode(senderId)
}

/**
 * 将一个数组([Array])的[MessageChain]对象转换成合并转发消息链([NodeMessageChain])
 * 并且返回已经构造的[NodeMessageChain]
 */
fun Array<MessageChain>.toNode(senderId: Long): NodeMessageChain {
    return this.toList().toNode(senderId)
}

/**
 * 将一个任意类型的集合[Collection]转换为一个构造好的消息链
 * 并且接收一个参数表示是否在每个元素之后插入一个换行符
 * ***注意: 元素必须重写了[toString]方法, 如果一个元素没有重写[toString]方法则会使用这个元素的内存地址***
 */
@JvmOverloads
fun <T> Collection<T?>.toMessageChain(newLine: Boolean = false, filterNull: Boolean = false): MessageChain {
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
fun <T> T?.toMessageChain() = MessageChain.Builder().addText(this.toString()).build()

/**
 * 将任意类型的数据转换成[MessageChain.Builder], 但是最终都会调用这个类型的[toString]方法
 */
fun <T> T?.toMessageChainBuilder() = MessageChain.Builder().addText(this.toString())

/**
 * 将一个任意类型的数组[Array]转换为一个构造完成的消息链
 * 接收一个参数表示是否在每个元素之后插入换行符
 */
@JvmOverloads
fun <T> Array<T>.toMessageChain(newLine: Boolean = false): MessageChain {
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
fun <T> Collection<T>.toMessageChainBuilder(newLine: Boolean = false): MessageChain.Builder {
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
fun <T> Array<T>.toMessageChainBuilder(newLine: Boolean = false): MessageChain.Builder {
    val msgBuilder = MessageChain.Builder()
    this.forEach {
        msgBuilder.addText(it.toString())
        if (newLine) msgBuilder.addNewLine()
    }
    return msgBuilder
}

/**
 * 使两个[MessageChain]对象可以快速拼接起来合并成一个
 * 完整的[MessageChain]
 */
operator fun MessageChain.plus(other: MessageChain): MessageChain {
    return this.finalArrayMsgList.addAll(other.finalArrayMsgList).toMessageChain()
}

/**
 * 使两个[MessageChain.Builder]对象可以快速拼接起来合并成一个
 * 完整的[MessageChain.Builder]
 */
operator fun MessageChain.Builder.plus(other: MessageChain.Builder): MessageChain.Builder {
    return this.arrayMessageList.addAll(other.arrayMessageList).toMessageChainBuilder()
}

/**
 * 对一个构造好的[MessageChain]使用+操作符快速添加一个[IPlainText]类型的消息段
 */
operator fun MessageChain.plus(other: Any): MessageChain {
    return this.finalArrayMsgList.add(IPlainText(IPlainText.Data(other.toString()))).toMessageChain()
}

/**
 * 将一个手动构造的Segment拼接成一个MessageChain
 */
operator fun Segment.plus(other: Segment): MessageChain {
    val msg = MessageChain.Builder()
    when (this) {
        is Text -> msg.addText(this.text)
        is AT -> msg.addAt(this.qq)
        is Face -> msg.addFace(this.id)
        is Image -> msg.addImage(this.file, base64)
        is cn.rtast.rob.segment.Record -> msg.addRecord(this.file)
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
    }
    when (other) {
        is Text -> msg.addText(other.text)
        is AT -> msg.addAt(other.qq)
        is Face -> msg.addFace(other.id)
        is Image -> msg.addImage(other.file)
        is Record -> msg.addRecord(other.file)
        is Video -> msg.addVideo(other.file)
        is Poke -> msg.addPoke(other.poke)
        is Reply -> msg.addReply(other.id)
        is XML -> msg.addXML(other.xml)
        is FriendContact -> msg.addContactFriend(other.id)
        is GroupContact -> msg.addContactGroup(other.id)
        is JSON -> msg.addJSON(other.json)
        is MusicShare -> msg.addMusicShare(other.type, other.id)
        is Rps -> msg.addRPS()
        is Dice -> msg.addDice()
        is Shake -> msg.addShake()
        is Share -> msg.addShare(other.url, other.title, other.content, other.image)
        is Location -> msg.addLocation(other.lat, other.lon, other.title, other.content)
        is CustomMusicShare -> msg.addCustomMusicShare(other.url, other.audio, other.title, other.content, other.image)
        is NewLine -> msg.addNewLine(other.times)
        is QFace -> msg.addFace(other.id)
    }
    return msg.build()
}

/**
 * 对一个[MessageChain]对象使用+操作符拼接[Segment]
 * 这个操作符的使用是为了连接[Segment.plus]方法
 */
operator fun MessageChain.plus(segment: Segment): MessageChain {
    val newBuilder = MessageChain.Builder()
    newBuilder.arrayMessageList.addAll(this.finalArrayMsgList)
    newBuilder.addSegment(segment)
    return newBuilder.build()
}

/**
 * [Segment]+[String]拼接
 */
operator fun Segment.plus(other: String): MessageChain {
    return MessageChain.Builder().addSegment(this).addText(other).build()
}

/**
 * [String]+[Segment]拼接
 */
operator fun String.plus(other: Segment): MessageChain {
    return MessageChain.Builder().addText(this).addSegment(other).build()
}