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
inline fun messageChain(builder: MessageChain.Builder.() -> Unit) =
    MessageChain.Builder().apply(builder).build()

/**
 * 添加一个纯文本
 */
inline fun MessageChain.Builder.text(text: Text.() -> Unit) =
    this.add(Text("").apply(text))

/**
 * 添加纯文本
 */
fun MessageChain.Builder.text(text: String) = this.addText(text)

/**
 * 添加纯文本并追加一个换行符
 */
inline fun MessageChain.Builder.textLine(text: Text.() -> Unit) {
    this.add(Text("").apply(text))
    this.add(NewLine(1))
}

/**
 * 添加纯文本并追加一个换行符
 */
fun MessageChain.Builder.textLine(text: String) = this.addTextLine(text)

/**
 * 添加@
 */
inline fun MessageChain.Builder.at(at: AT.() -> Unit) =
    this.add(AT(-1L).apply(at))

/**
 * 添加@
 */
inline fun MessageChain.Builder.mention(mention: Mention.() -> Unit) =
    this.add(Mention(-1L).apply(mention))

/**
 * 添加@
 */
fun MessageChain.Builder.at(qq: Long) = this.addAt(qq)
/**
 * 添加@
 */
fun MessageChain.Builder.mention(qq: Long) = this.addMention(qq)

/**
 * 通过Int 表情id添加表情
 */
inline fun MessageChain.Builder.face(face: Face.() -> Unit) =
    this.add(Face(0).apply(face))

/**
 * 通过Int 表情id添加表情
 */
fun MessageChain.Builder.face(faceId: Int) = this.addFace(faceId)

/**
 * 通过枚举类来添加表情
 */
inline fun MessageChain.Builder.qface(qface: QFace.() -> Unit) =
    this.add(QFace(QQFace.AnZhongGuanCha).apply(qface))

/**
 * 通过枚举类来添加表情
 */
fun MessageChain.Builder.qface(qface: QQFace) = this.addFace(qface)

/**
 * 添加图片
 */
inline fun MessageChain.Builder.image(image: Image.() -> Unit) =
    this.add(Image("".toResource(), false).apply(image))

/**
 * 添加图片
 */
fun MessageChain.Builder.image(resource: Resource) = this.addImage(resource)

/**
 * 添加语音
 */
inline fun MessageChain.Builder.record(record: Record.() -> Unit) =
    this.add(Record("").apply(record))

/**
 * 添加视频
 */
inline fun MessageChain.Builder.video(video: Video.() -> Unit) =
    this.add(Video("").apply(video))

/**
 * 添加视频
 */
fun MessageChain.Builder.video(file: String) = this.addVideo(file)

/**
 * 添加戳一戳 | 旧版
 */
inline fun MessageChain.Builder.poke(poke: Poke.() -> Unit) =
    this.add(Poke(PokeMessage.Poke).apply(poke))

/**
 * 添加戳一戳 | 旧版
 */
fun MessageChain.Builder.poke(poke: PokeMessage) = this.addPoke(poke)

/**
 * 添加回复
 */
inline fun MessageChain.Builder.reply(reply: Reply.() -> Unit) =
    this.add(Reply(0L).apply(reply))

/**
 * 添加回复
 */
fun MessageChain.Builder.reply(messageId: Long) = this.addReply(messageId)

/**
 * 添加xml
 */
inline fun MessageChain.Builder.xml(xml: XML.() -> Unit) =
    this.add(XML("").apply(xml))

/**
 * 添加xml
 */
fun MessageChain.Builder.xml(xml: String) = this.addXML(xml)

/**
 * 添加好友推荐
 */
inline fun MessageChain.Builder.friendContact(contact: FriendContact.() -> Unit) =
    this.add(FriendContact(0L).apply(contact))

/**
 * 添加好友推荐
 */
fun MessageChain.Builder.friendContact(userId: Long) = this.addContactFriend(userId)

/**
 * 添加群聊推荐
 */
inline fun MessageChain.Builder.groupContact(contact: GroupContact.() -> Unit) =
    this.add(GroupContact(0L).apply(contact))

/**
 * 添加群聊推荐
 */
fun MessageChain.Builder.groupContact(groupId: Long) = this.addContactGroup(groupId)

/**
 * 添加json
 */
inline fun MessageChain.Builder.json(json: JSON.() -> Unit) =
    this.add(JSON("").apply(json))

/**
 * 添加json
 */
fun MessageChain.Builder.json(json: String) = this.addJSON(json)

/**
 * 添加音乐分享
 */
inline fun MessageChain.Builder.music(music: MusicShare.() -> Unit) =
    this.add(MusicShare(MusicShareType.Netease, "0").apply(music))

/**
 * 添加音乐分享
 */
fun MessageChain.Builder.music(type: MusicShareType, id: String) = this.addMusicShare(type, id)

/**
 * 添加换行
 */
inline fun MessageChain.Builder.newline(newLine: NewLine.() -> Unit) =
    this.add(NewLine(1).apply(newLine))

/**
 * 添加换行
 */
fun MessageChain.Builder.newline(count: Int = 1) = this.addNewLine(count)

/**
 * 添加链接分享
 */
inline fun MessageChain.Builder.share(share: Share.() -> Unit) =
    this.add(Share("", "", null, null).apply(share))

/**
 * 添加链接分享
 */
fun MessageChain.Builder.share(
    url: String,
    title: String,
    content: String? = null,
    image: String? = null
) = this.addShare(url, title, content, image)

/**
 * 添加位置
 */
inline fun MessageChain.Builder.location(location: Location.() -> Unit) =
    this.add(Location(0.0, 0.0, null, null).apply(location))

/**
 * 添加位置
 */
fun MessageChain.Builder.location(
    lat: Double,
    lon: Double,
    title: String? = null,
    content: String? = null,
) = this.addLocation(lat, lon, title, content)

/**
 * 添加自定义音乐分享
 */
inline fun MessageChain.Builder.customMusic(customMusic: CustomMusicShare.() -> Unit) =
    this.add(CustomMusicShare("", "", "", null, null).apply(customMusic))

/**
 * 添加自定义音乐分享
 */
fun MessageChain.Builder.customMusic(
    url: String,
    audio: String,
    title: String,
    content: String? = null,
    image: String? = null
) = this.addCustomMusicShare(url, audio, title, content, image)

/**
 * 添加空格
 */
inline fun MessageChain.Builder.spaces(spaces: Spaces.() -> Unit) =
    this.add(Spaces(1).apply(spaces))

/**
 * 添加空格
 */
fun MessageChain.Builder.spaces(count: Int = 1) = this.addSpaces(count)

/**
 * 添加剪刀石头布
 */
inline fun MessageChain.Builder.rps(rps: Rps.() -> Unit) =
    this.add(Rps().apply(rps))

/**
 * 添加剪刀石头布
 */
fun MessageChain.Builder.rps() = this.addRPS()

/**
 * 添加骰子
 */
inline fun MessageChain.Builder.dice(dice: Dice.() -> Unit) =
    this.add(Dice().apply(dice))

/**
 * 添加骰子
 */
fun MessageChain.Builder.dice() = this.addDice()

/**
 * 添加晃屏
 */
inline fun MessageChain.Builder.shake(shake: Shake.() -> Unit) =
    this.add(Shake().apply(shake))

/**
 * 添加晃屏
 */
fun MessageChain.Builder.shake() = this.addShake()

/**
 * 添加@全体
 */
inline fun MessageChain.Builder.atAll(atAll: AtAll.() -> Unit) =
    this.add(AtAll().apply(atAll))

/**
 * 添加@全体
 */
inline fun MessageChain.Builder.mentionAll(mentionAll: MentionAll.() -> Unit) =
    this.add(MentionAll().apply(mentionAll))

/**
 * 添加@全体
 */
fun MessageChain.Builder.atAll() = this.addAtAll()

/**
 * 添加@全体
 */
fun MessageChain.Builder.mentionAll() = this.addMentionAll()