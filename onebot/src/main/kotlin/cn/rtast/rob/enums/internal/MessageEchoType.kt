/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.enums.internal

/**
 * 仅限内部使用用于[cn.rtast.rob.util.ob.OneBotAction.createCompletableDeferred]的识别符
 * 具体表现在了Websocket发送的数据包中的`echo`字段, ROneBot依靠这个字段来识别每次的请求结果
 * 并将其正确的分发的调用的位置
 */
internal enum class MessageEchoType {
    CanSendImage, CanSendRecord, GetForwardMessage,
    GetFriendList, GetGroupInfo, GetGroupList,
    GetGroupMemberList, GetGroupMemberInfo,
    GetLoginInfo, GetMessage, GetStrangerInfo,
    GetVersionInfo, FetchCustomFace,
    SendForwardMsg, GetGroupRootFiles,
    GetGroupFilesByFolder, GetGroupFileUrl,
    GetGroupNotice, ReleaseGroupNotice,
    GetEssenceMessageList, GetGroupHonorInfo,
    GetCSRFToken, GetGroupMessageHistory,
    GetPrivateMessageHistory, GetStatus,
    GetCookies, SendGroupMessage, SendPrivateMessage,
    CallCustomApi, UploadImage, SetBotAvatar,
    FetchMFaceKey, SetGroupAvatar, OCRImage,
    GetFriendWithCategory, GetGroupIgnoreAddRequest,
    GetGroupAtAllRemain, GetGroupFileSystemInfo,
    GetRobotUinRange
}