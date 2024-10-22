/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.enums.internal

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
    FetchMFaceKey, SetGroupAvatar
}