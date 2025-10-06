/*
 * Copyright © 2025 RTAkland
 * Date: 10/6/25, 2:56 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session

import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.segment.Segment
import cn.rtast.rob.segment.Text
import cn.rtast.rob.segment.toMessageChain

// group extended reply message type

/**
 * 结束会话
 * 可选的对消息进行回复
 * 群聊
 */
public suspend fun GroupSessionStruct<GroupMessage>.accept(feedback: String): Unit? =
    accept(Text(feedback))

/**
 * 结束会话
 * 可选的对消息进行回复
 * 群聊
 */
public suspend fun GroupSessionStruct<GroupMessage>.accept(feedback: Segment): Unit? =
    accept(feedback.toMessageChain())

/**
 * 结束会话
 * 可选的对消息进行回复
 * 群聊
 */
public suspend fun GroupSessionStruct<GroupMessage>.accept(feedback: List<Segment>): Unit? =
    accept(feedback.toMessageChain())

//////////////////////////////////////////////////////

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 群聊
 */
public suspend fun GroupSessionStruct<GroupMessage>.reject(feedback: String): Unit? =
    accept(Text(feedback).toMessageChain())

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 群聊
 */
public suspend fun GroupSessionStruct<GroupMessage>.reject(feedback: Segment): Unit? =
    accept(feedback.toMessageChain())

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 群聊
 */
public suspend fun GroupSessionStruct<GroupMessage>.reject(feedback: List<Segment>): Unit? =
    accept(feedback.toMessageChain())

// private


/**
 * 结束会话
 * 可选的对消息进行回复
 * 私聊
 */
public suspend fun PrivateSessionStruct<PrivateMessage>.accept(feedback: String): Unit? =
    accept(Text(feedback))

/**
 * 结束会话
 * 可选的对消息进行回复
 * 私聊
 */
public suspend fun PrivateSessionStruct<PrivateMessage>.accept(feedback: Segment): Unit? =
    accept(feedback.toMessageChain())

/**
 * 结束会话
 * 可选的对消息进行回复
 * 私聊
 */
public suspend fun PrivateSessionStruct<PrivateMessage>.accept(feedback: List<Segment>): Unit? =
    accept(feedback.toMessageChain())

//////////////////////////////////////////////////////


/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 私聊
 */
public suspend fun PrivateSessionStruct<PrivateMessage>.reject(feedback: String): Unit? =
    accept(Text(feedback).toMessageChain())

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 私聊
 */
public suspend fun PrivateSessionStruct<PrivateMessage>.reject(feedback: Segment): Unit? =
    accept(feedback.toMessageChain())

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 私聊
 */
public suspend fun PrivateSessionStruct<PrivateMessage>.reject(feedback: List<Segment>): Unit? =
    accept(feedback.toMessageChain())