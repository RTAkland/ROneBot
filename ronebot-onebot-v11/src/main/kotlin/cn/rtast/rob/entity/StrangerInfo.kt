/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import cn.rtast.rob.enums.BusinessName
import cn.rtast.rob.enums.StatusId
import cn.rtast.rob.enums.UserSex
import com.google.gson.annotations.SerializedName

/**
 * 陌生人信息
 */
data class StrangerInfo(
    val data: StrangerInfo,
) {
    data class StrangerInfo(
        /**
         * QQ号
         */
        @SerializedName("user_id")
        val userId: Long,
        /**
         * qid身份卡
         */
        @SerializedName("q_id")
        val qId: String,
        /**
         * 昵称
         */
        val nickname: String,
        /**
         * 性别
         */
        val sex: UserSex,
        /**
         * 年龄
         */
        val age: Int,
        /**
         * 等级
         */
        val level: Int,
        /**
         * 头像URL
         */
        val avatar: String,
        /**
         * 签名
         */
        val sign: String,
        /**
         * 状态
         */
        val status: Status,
        /**
         * 注册时间
         */
        @SerializedName("RegisterTime")
        val registerTime: String,
        /**
         * 开通的业务
         */
        @SerializedName("Business")
        val business: List<Business>,
    )

    data class Status(
        /**
         * 状态的ID
         */
        @SerializedName("status_id")
        val statusId: UInt,
        /**
         * 状态上附带的表情ID
         */
        @SerializedName("face_id")
        val faceId: Int,
        /**
         * 状态描述文本
         */
        val message: String,
    ) {
        fun getStatus() = StatusId.entries.find { it.statusId == statusId }
    }

    data class Business(
        /**
         * 业务类型
         */
        val type: Int,
        /**
         * 业务名称
         */
        val name: String,
        /**
         * 业务等级
         */
        val level: Int,
        /**
         * 业务图标URL
         */
        val icon: String,
        /**
         * 是否为大/超级会员
         */
        @SerializedName("ispro")
        val isPro: Int,
        /**
         * 是否为年费
         */
        @SerializedName("isyear")
        val isYear: Int,
    ) {
        /**
         * 通过type id来获取对应的枚举类
         */
        fun getType() = BusinessName.forType(type)

        /**
         * 通过名字来获取对应的枚举类
         */
        fun getTypeByName() = BusinessName.forName(name)
    }
}