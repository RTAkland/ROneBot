/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:41 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.enums.UserSex
import cn.rtast.rob.milky.enums.internal.ApiStatus
import kotlinx.serialization.Serializable

/**
 * 获取用户个人信息
 */
@Serializable
public data class GetUserProfile(
    val data: UserProfile?,
    val status: ApiStatus,
    val message: String?,
) {
    @Serializable
    public data class UserProfile(
        /**
         * 昵称
         */
        val nickname: String,

        /**
         * qid
         */
        val qid: String,
        /**
         * 年龄
         */
        val age: Int,
        /**
         * 性别
         */
        val sex: UserSex,
        /**
         * 备注
         */
        val remark: String,
        /**
         * 个性签名
         */
        val bio: String,
        /**
         * qq等级
         */
        val level: Int,
        /**
         * 国家
         */
        val country: String,
        /**
         * 城市
         */
        val city: String,
        /**
         * 学校
         */
        val school: String,
    )
}