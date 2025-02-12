/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.enums


/**
 * 腾讯的业务ID+名称,
 * 来自Lagrange.Onebot
 * [GetStrangerInfoOperation.cs](https://github.com/LagrangeDev/Lagrange.Core/blob/master/Lagrange.OneBot/Core/Operation/Info/GetStrangerInfoOperation.cs)
 */
enum class BusinessName(val type: Int, val description: String) {
    BigVIP(113, "QQ大会员"), VIP(1, "QQ会员"), YellowDiamond(102, "黄钻"),
    CoupleVIP(119, "情侣会员"), GreenDiamond(103, "绿钻"), TencentVideo(4, "腾讯视频"),
    BigBigSuperVIP(108, "大王超级会员"), CoupleDiamond(104, "情侣超级钻"),
    WeShareVIP(105, "微云会员"), RedDiamond(101, "红钻"), CF_VIP(115, "cf游戏特权"),
    BlueDiamond(118, "蓝钻"), SVIP_TENCENT_VIDEO(107, "SVIP+腾讯视频");

    companion object {
        fun forName(name: String): BusinessName? {
            return entries.find { it.description == name }
        }

        fun forType(type: Int): BusinessName? {
            return entries.find { it.type == type }
        }
    }
}