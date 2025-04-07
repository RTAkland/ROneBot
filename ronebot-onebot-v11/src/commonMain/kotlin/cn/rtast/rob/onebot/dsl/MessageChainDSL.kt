/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/27
 */

@file:Suppress("unused", "DEPRECATION")

package cn.rtast.rob.onebot.dsl

import cn.rtast.rob.entity.Resource
import cn.rtast.rob.entity.toResource
import cn.rtast.rob.enums.MusicShareType
import cn.rtast.rob.enums.PokeMessage
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.segment.*

/**
 * dsl 风格的构造一个[MessageChain]
 */
public inline fun messageChain(builder: MessageChain.Builder.() -> Unit): MessageChain {
    return MessageChain.Builder().apply(builder).build()
}

/**
 * 防止在dsl消息链中嵌套消息链
 */
@Deprecated(message = "消息链内不允许嵌套消息链", level = DeprecationLevel.ERROR)
public inline fun MessageChain.Builder.messageChain(builder: (@MessageChainDsl MessageChain.Builder).() -> Unit) = Unit

/**
 * 添加一个纯文本
 */
public inline fun MessageChain.Builder.text(text: (@MessageChainDsl Text).() -> Unit): MessageChain.Builder =
    this.add(Text("").apply(text))

/**
 * 添加纯文本
 */
public fun MessageChain.Builder.text(text: String): MessageChain.Builder = this.addText(text)

/**
 * 添加纯文本并追加一个换行符
 */
public inline fun MessageChain.Builder.textLine(text: (@MessageChainDsl Text).() -> Unit) {
    this.add(Text("").apply(text))
    this.add(NewLine(1))
}

/**
 * 添加纯文本并追加一个换行符
 */
public fun MessageChain.Builder.textLine(text: String): MessageChain.Builder = this.addTextLine(text)

/**
 * 添加@
 * 已弃用
 */
@Deprecated("改方法已弃用, 请使用mention")
public inline fun MessageChain.Builder.at(at: (@MessageChainDsl At).() -> Unit): MessageChain.Builder =
    this.add(At(-1L).apply(at))

/**
 * 添加@
 */
public inline fun MessageChain.Builder.mention(mention: (@MessageChainDsl Mention).() -> Unit): MessageChain.Builder =
    this.add(Mention(-1L).apply(mention))

/**
 * 添加@
 */
public fun MessageChain.Builder.at(qq: Long): MessageChain.Builder = this.addAt(qq)

/**
 * 添加@
 */
public fun MessageChain.Builder.mention(qq: Long): MessageChain.Builder = this.addMention(qq)

/**
 * 通过Int 表情id添加表情
 */
public inline fun MessageChain.Builder.face(face: (@MessageChainDsl Face).() -> Unit): MessageChain.Builder =
    this.add(Face(0).apply(face))

/**
 * 通过Int 表情id添加表情
 */
public fun MessageChain.Builder.face(faceId: Int): MessageChain.Builder = this.addFace(faceId)

/**
 * 通过枚举类来添加表情
 */
public inline fun MessageChain.Builder.qface(qface: (@MessageChainDsl QFace).() -> Unit): MessageChain.Builder =
    this.add(QFace(QQFace.AnZhongGuanCha).apply(qface))

/**
 * 通过枚举类来添加表情
 */
public fun MessageChain.Builder.qface(qface: QQFace): MessageChain.Builder = this.addFace(qface)

/**
 * 添加图片
 */
public inline fun MessageChain.Builder.image(image: (@MessageChainDsl Image).() -> Unit): MessageChain.Builder =
    this.add(Image("".toResource(), false).apply(image))

/**
 * 添加图片
 */
public fun MessageChain.Builder.image(resource: Resource): MessageChain.Builder = this.addImage(resource)

/**
 * 添加语音
 */
public inline fun MessageChain.Builder.record(record: (@MessageChainDsl Record).() -> Unit): MessageChain.Builder =
    this.add(Record("").apply(record))

/**
 * 添加视频
 */
public inline fun MessageChain.Builder.video(video: (@MessageChainDsl Video).() -> Unit): MessageChain.Builder =
    this.add(Video("").apply(video))

/**
 * 添加视频
 */
public fun MessageChain.Builder.video(file: String): MessageChain.Builder = this.addVideo(file)

/**
 * 添加戳一戳 | 旧版
 */
public inline fun MessageChain.Builder.poke(poke: (@MessageChainDsl Poke).() -> Unit): MessageChain.Builder =
    this.add(Poke(PokeMessage.Poke).apply(poke))

/**
 * 添加戳一戳 | 旧版
 */
public fun MessageChain.Builder.poke(poke: PokeMessage): MessageChain.Builder = this.addPoke(poke)

/**
 * 添加回复
 */
public inline fun MessageChain.Builder.reply(reply: (@MessageChainDsl Reply).() -> Unit): MessageChain.Builder =
    this.add(Reply(0L).apply(reply))

/**
 * 添加回复
 */
public fun MessageChain.Builder.reply(messageId: Long): MessageChain.Builder = this.addReply(messageId)

/**
 * 添加xml
 */
public inline fun MessageChain.Builder.xml(xml: (@MessageChainDsl XML).() -> Unit): MessageChain.Builder =
    this.add(XML("").apply(xml))

/**
 * 添加xml
 */
public fun MessageChain.Builder.xml(xml: String): MessageChain.Builder = this.addXML(xml)

/**
 * 添加好友推荐
 */
public inline fun MessageChain.Builder.friendContact(contact: (@MessageChainDsl FriendContact).() -> Unit): MessageChain.Builder =
    this.add(FriendContact(0L).apply(contact))

/**
 * 添加好友推荐
 */
public fun MessageChain.Builder.friendContact(userId: Long): MessageChain.Builder = this.addContactFriend(userId)

/**
 * 添加群聊推荐
 */
public inline fun MessageChain.Builder.groupContact(contact: (@MessageChainDsl GroupContact).() -> Unit): MessageChain.Builder =
    this.add(GroupContact(0L).apply(contact))

/**
 * 添加群聊推荐
 */
public fun MessageChain.Builder.groupContact(groupId: Long): MessageChain.Builder = this.addContactGroup(groupId)

/**
 * 添加json
 */
public inline fun MessageChain.Builder.json(json: (@MessageChainDsl JSON).() -> Unit): MessageChain.Builder =
    this.add(JSON("").apply(json))

/**
 * 添加json
 */
public fun MessageChain.Builder.json(json: String): MessageChain.Builder = this.addJSON(json)

/**
 * 添加音乐分享
 */
public inline fun MessageChain.Builder.music(music: (@MessageChainDsl MusicShare).() -> Unit): MessageChain.Builder =
    this.add(MusicShare(MusicShareType.Netease, "0").apply(music))

/**
 * 添加音乐分享
 */
public fun MessageChain.Builder.music(type: MusicShareType, id: String): MessageChain.Builder =
    this.addMusicShare(type, id)

/**
 * 添加换行
 */
public inline fun MessageChain.Builder.newline(newLine: (@MessageChainDsl NewLine).() -> Unit): MessageChain.Builder =
    this.add(NewLine(1).apply(newLine))

/**
 * 添加换行
 */
public fun MessageChain.Builder.newline(count: Int = 1): MessageChain.Builder = this.addNewLine(count)

/**
 * 添加链接分享
 */
public inline fun MessageChain.Builder.share(share: (@MessageChainDsl Share).() -> Unit): MessageChain.Builder =
    this.add(Share("", "", null, null).apply(share))

/**
 * 添加链接分享
 */
public fun MessageChain.Builder.share(
    url: String,
    title: String,
    content: String? = null,
    image: String? = null
): MessageChain.Builder = this.addShare(url, title, content, image)

/**
 * 添加位置
 */
public inline fun MessageChain.Builder.location(location: (@MessageChainDsl Location).() -> Unit): MessageChain.Builder =
    this.add(Location(0.0, 0.0, null, null).apply(location))

/**
 * 添加位置
 */
public fun MessageChain.Builder.location(
    lat: Double,
    lon: Double,
    title: String? = null,
    content: String? = null,
): MessageChain.Builder = this.addLocation(lat, lon, title, content)

/**
 * 添加自定义音乐分享
 */
public inline fun MessageChain.Builder.customMusic(customMusic: (@MessageChainDsl CustomMusicShare).() -> Unit): MessageChain.Builder =
    this.add(CustomMusicShare("", "", "", null, null).apply(customMusic))

/**
 * 添加自定义音乐分享
 */
public fun MessageChain.Builder.customMusic(
    url: String,
    audio: String,
    title: String,
    content: String? = null,
    image: String? = null
): MessageChain.Builder = this.addCustomMusicShare(url, audio, title, content, image)

/**
 * 添加空格
 */
public inline fun MessageChain.Builder.spaces(spaces: (@MessageChainDsl Spaces).() -> Unit): MessageChain.Builder =
    this.add(Spaces(1).apply(spaces))

/**
 * 添加空格
 */
public fun MessageChain.Builder.spaces(count: Int = 1): MessageChain.Builder = this.addSpaces(count)

/**
 * 添加剪刀石头布
 */
public inline fun MessageChain.Builder.rps(rps: (@MessageChainDsl Rps).() -> Unit): MessageChain.Builder =
    this.add(Rps().apply(rps))

/**
 * 添加剪刀石头布
 */
public fun MessageChain.Builder.rps(): MessageChain.Builder = this.addRPS()

/**
 * 添加骰子
 */
public inline fun MessageChain.Builder.dice(dice: (@MessageChainDsl Dice).() -> Unit): MessageChain.Builder =
    this.add(Dice().apply(dice))

/**
 * 添加骰子
 */
public fun MessageChain.Builder.dice(): MessageChain.Builder = this.addDice()

/**
 * 添加晃屏
 */
public inline fun MessageChain.Builder.shake(shake: (@MessageChainDsl Shake).() -> Unit): MessageChain.Builder =
    this.add(Shake().apply(shake))

/**
 * 添加晃屏
 */
public fun MessageChain.Builder.shake(): MessageChain.Builder = this.addShake()

/**
 * 添加@全体
 * 已弃用
 */
@Deprecated("该方法已弃用, 请使用mentionAll方法")
public inline fun MessageChain.Builder.atAll(atAll: (@MessageChainDsl AtAll).() -> Unit): MessageChain.Builder =
    this.add(AtAll().apply(atAll))

/**
 * 添加@全体
 */
public inline fun MessageChain.Builder.mentionAll(mentionAll: (@MessageChainDsl MentionAll).() -> Unit): MessageChain.Builder =
    this.add(MentionAll().apply(mentionAll))

/**
 * 添加@全体
 * 已弃用
 */
@Deprecated("该方法已弃用, 请使用mentionAll方法")
public fun MessageChain.Builder.atAll(): MessageChain.Builder = this.addAtAll()

/**
 * 添加@全体
 */
public fun MessageChain.Builder.mentionAll(): MessageChain.Builder = this.addMentionAll()