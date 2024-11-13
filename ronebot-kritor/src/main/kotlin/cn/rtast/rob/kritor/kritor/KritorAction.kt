/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */

@file:Suppress("unused")

package cn.rtast.rob.kritor.kritor

import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.kritor.BotInstance
import io.kritor.common.Contact
import io.kritor.common.ForwardMessageBody
import io.kritor.common.Scene
import io.kritor.common.contact
import io.kritor.message.DeleteEssenceMessageResponse
import io.kritor.message.DownloadForwardMessageResponse
import io.kritor.message.GetEssenceMessageListResponse
import io.kritor.message.GetHistoryMessageResponse
import io.kritor.message.GetMessageBySeqResponse
import io.kritor.message.GetMessageResponse
import io.kritor.message.MessageServiceGrpcKt
import io.kritor.message.ReactMessageWithEmojiResponse
import io.kritor.message.RecallMessageResponse
import io.kritor.message.SendMessageResponse
import io.kritor.message.SetEssenceMessageResponse
import io.kritor.message.SetMessageReadResponse
import io.kritor.message.UploadForwardMessageResponse
import io.kritor.message.deleteEssenceMessageRequest
import io.kritor.message.downloadForwardMessageRequest
import io.kritor.message.getEssenceMessageListRequest
import io.kritor.message.getHistoryMessageRequest
import io.kritor.message.getMessageBySeqRequest
import io.kritor.message.getMessageRequest
import io.kritor.message.reactMessageWithEmojiRequest
import io.kritor.message.recallMessageRequest
import io.kritor.message.sendMessageRequest
import io.kritor.message.setEssenceMessageRequest
import io.kritor.message.setMessageReadRequest
import io.kritor.message.uploadForwardMessageRequest

class KritorAction internal constructor(
    private val botInstance: BotInstance
) {

    private val stub = MessageServiceGrpcKt.MessageServiceCoroutineStub(botInstance.interceptedChannel)

    /**
     * 发送群消息
     */
    suspend fun sendGroupMessage(groupId: Long, message: MessageChain): SendMessageResponse {
        return stub.sendMessage(sendMessageRequest {
            this.contact = contact {
                this.scene = Scene.GROUP
                this.peer = groupId.toString()
            }
            this.elements.addAll(message.elements)
        })
    }

    /**
     * 标记一条消息已读
     */
    suspend fun setMessageRead(contact: Contact): SetMessageReadResponse {
        return stub.setMessageReaded(setMessageReadRequest {
            this.contact = contact
        })
    }

    /**
     * 撤回消息
     */
    suspend fun recallMessage(contact: Contact, messageId: String): RecallMessageResponse {
        return stub.recallMessage(recallMessageRequest {
            this.contact = contact
            this.messageId = messageId
        })
    }

    /**
     * 用表情回应消息
     */
    suspend fun reactMessageWithEmoji(
        contact: Contact,
        messageId: String,
        face: QQFace,
        isSet: Boolean = false
    ): ReactMessageWithEmojiResponse {
        return stub.reactMessageWithEmoji(reactMessageWithEmojiRequest {
            this.contact = contact
            this.messageId = messageId
            this.faceId = face.id
            this.isSet = isSet
        })
    }

    /**
     * 获取消息
     */
    suspend fun getMessage(contact: Contact, messageId: String): GetMessageResponse {
        return stub.getMessage(getMessageRequest {
            this.contact = contact
            this.messageId = messageId
        })
    }

    /**
     * 通过SeqId来获取消息
     */
    suspend fun getMessageByResId(contact: Contact, messageSeq: Long): GetMessageBySeqResponse {
        return stub.getMessageBySeq(getMessageBySeqRequest {
            this.contact = contact
            this.messageSeq = messageSeq
        })
    }

    /**
     * 通过消息id来获取消息记录
     */
    suspend fun getHistoryMessage(
        contact: Contact,
        startMessageId: String = "",
        count: Int = 3
    ): GetHistoryMessageResponse {
        return stub.getHistoryMessage(getHistoryMessageRequest {
            this.contact = contact
            this.startMessageId = startMessageId
            this.count = count
        })
    }

    /**
     * 上传转发消息节点
     * WIP
     */
    @Deprecated("Not implemented yet", level = DeprecationLevel.HIDDEN)
    suspend fun uploadForwardMessage(
        contact: Contact,
        messageBody: ForwardMessageBody,
        retryCount: Int = 3
    ): UploadForwardMessageResponse {
        return stub.uploadForwardMessage(uploadForwardMessageRequest {
            this.contact = contact
            this.retryCount = retryCount
        })
    }

    /**
     * 获取合并转发消息
     */
    suspend fun downloadForwardMessage(resId: String): DownloadForwardMessageResponse {
        return stub.downloadForwardMessage(downloadForwardMessageRequest {
            this.resId = resId
        })
    }

    /**
     * 获取群精华消息列表
     */
    suspend fun getEssenceMessageList(groupId: Long, page: Int, pageSize: Int): GetEssenceMessageListResponse {
        return stub.getEssenceMessageList(getEssenceMessageListRequest {
            this.groupId = groupId
            this.page = page
            this.pageSize = pageSize
        })
    }

    /**
     * 设置群精华消息
     */
    suspend fun setEssenceMessage(groupId: Long, messageId: String): SetEssenceMessageResponse {
        return stub.setEssenceMessage(setEssenceMessageRequest {
            this.groupId = groupId
            this.messageId = messageId
        })
    }

    /**
     * 删除群精华消息
     */
    suspend fun deleteEssenceMessage(groupId: Long, messageId: String): DeleteEssenceMessageResponse {
        return stub.deleteEssenceMessage(deleteEssenceMessageRequest {
            this.messageId = messageId
            this.groupId = groupId
        })
    }

    suspend fun getFriendList(refresh: Boolean = false) {
    }
}