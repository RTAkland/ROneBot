/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/27
 */

@file:Suppress("unused", "DEPRECATION")

package cn.rtast.rob.onebot.dsl

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
 * 添加@
 */
inline fun MessageChain.Builder.at(at: AT.() -> Unit) =
    this.add(AT(0L).apply(at))

/**
 * 通过Int 表情id添加表情
 */
inline fun MessageChain.Builder.face(face: Face.() -> Unit) =
    this.add(Face(0).apply(face))

/**
 * 通过枚举类来添加表情
 */
inline fun MessageChain.Builder.qface(qface: QFace.() -> Unit) =
    this.add(QFace(QQFace.AnZhongGuanCha).apply(qface))

/**
 * 添加图片
 */
inline fun MessageChain.Builder.image(image: Image.() -> Unit) =
    this.add(Image("", false).apply(image))

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
 * 添加戳一戳 | 旧版
 */
inline fun MessageChain.Builder.poke(poke: Poke.() -> Unit) =
    this.add(Poke(PokeMessage.Poke).apply(poke))

/**
 * 添加回复
 */
inline fun MessageChain.Builder.reply(reply: Reply.() -> Unit) =
    this.add(Reply(0L).apply(reply))

/**
 * 添加xml
 */
inline fun MessageChain.Builder.xml(xml: XML.() -> Unit) =
    this.add(XML("").apply(xml))

/**
 * 添加好友推荐
 */
inline fun MessageChain.Builder.friendContact(contact: FriendContact.() -> Unit) =
    this.add(FriendContact(0L).apply(contact))

/**
 * 添加群聊推荐
 */
inline fun MessageChain.Builder.groupContact(contact: GroupContact.() -> Unit) =
    this.add(GroupContact(0L).apply(contact))

/**
 * 添加json
 */
inline fun MessageChain.Builder.json(json: JSON.() -> Unit) =
    this.add(JSON("").apply(json))

/**
 * 添加音乐分享
 */
inline fun MessageChain.Builder.music(music: MusicShare.() -> Unit) =
    this.add(MusicShare(MusicShareType.Netease, "0").apply(music))

/**
 * 添加换行
 */
inline fun MessageChain.Builder.newline(newLine: NewLine.() -> Unit) =
    this.add(NewLine(1).apply(newLine))

/**
 * 添加链接分享
 */
inline fun MessageChain.Builder.share(share: Share.() -> Unit) =
    this.add(Share("", "", null, null).apply(share))

/**
 * 添加位置
 */
inline fun MessageChain.Builder.location(location: Location.() -> Unit) =
    this.add(Location(0.0, 0.0, null, null).apply(location))

/**
 * 添加自定义音乐分享
 */
inline fun MessageChain.Builder.customMusic(customMusic: CustomMusicShare.() -> Unit) =
    this.add(CustomMusicShare("", "", "", null, null, "custom").apply(customMusic))

/**
 * 添加空格
 */
inline fun MessageChain.Builder.spaces(spaces: Spaces.() -> Unit) =
    this.add(Spaces(1).apply(spaces))

/**
 * 添加剪刀石头布
 */
inline fun MessageChain.Builder.rps(rps: Rps.() -> Unit) =
    this.add(Rps().apply(rps))

/**
 * 添加骰子
 */
inline fun MessageChain.Builder.dice(dice: Dice.() -> Unit) =
    this.add(Dice().apply(dice))

/**
 * 添加晃屏
 */
inline fun MessageChain.Builder.shake(shake: Shake.() -> Unit) =
    this.add(Shake().apply(shake))

/**
 * 添加@全体
 */
inline fun MessageChain.Builder.atAll(atAll: AtAll.() -> Unit) =
    this.add(AtAll().apply(atAll))
