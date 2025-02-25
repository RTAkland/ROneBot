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

data class StrangerInfo(
    val data: StrangerInfo,
) {
    data class StrangerInfo(
        @SerializedName("user_id")
        val userId: Long,
        @SerializedName("q_id")
        val qId: String,
        val nickname: String,
        val sex: UserSex,
        val age: Int,
        val level: Int,
        val avatar: String,
        val sign: String,
        val status: Status,
        @SerializedName("RegisterTime")
        val registerTime: String,
        @SerializedName("Business")
        val business: List<Business>,
    )

    data class Status(
        @SerializedName("status_id")
        val statusId: UInt,
        @SerializedName("face_id")
        val faceId: Int,
        val message: String,
    ) {
        fun getStatus() = StatusId.entries.find { it.statusId == statusId }
    }

    data class Business(
        val type: Int,
        val name: String,
        val level: Int,
        val icon: String,
        @SerializedName("ispro")
        val isPro: Int,
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