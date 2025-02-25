/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

/**
 * 用于获取用户在线状态的枚举类, 内容来自于Lagrange.OneBot
 * [GetStrangerInfoOperation.cs](https://github.com/LagrangeDev/Lagrange.Core/blob/1484051d2e3c7ceb206c334b7338d00138df1cc7/Lagrange.OneBot/Core/Operation/Info/GetStrangerInfoOperation.cs)
 */
enum class StatusId(val statusId: UInt, val message: String) {
    ONLINE(1u, "在线"),
    AWAY(3u, "离开"),
    INVISIBLE(4u, "隐身/离线"),
    BUSY(5u, "忙碌"),
    Q_ME(6u, "Q我吧"),
    DO_NOT_DISTURB(7u, "请勿打扰"),
    LISTENING_TO_MUSIC(263169u, "听歌中"),
    MY_BATTERY(15205121u, "我的电量"),
    DOING_GOOD_THINGS(16713473u, "做好事"),
    OUT_FOR_FUN(13829889u, "出去浪"),
    TRAVELING(14616321u, "去旅行"),
    EMPTY(14550785u, "被掏空"),
    STEPS_TODAY(14747393u, "今日步数"),
    WEATHER_TODAY(394241u, "今日天气"),
    CRUSHED(14878465u, "我crush了"),
    LOVE_YOU(14026497u, "爱你"),
    IN_LOVE(1770497u, "恋爱中"),
    GOOD_LUCK(3081217u, "好运锦鲤"),
    BAD_LUCK_BE_GONE(11600897u, "水逆退散"),
    HIGH_SPIRITED(2098177u, "嗨到飞起"),
    FULL_OF_ENERGY(2229249u, "元气满满"),
    HARD_TO_EXPLAIN(2556929u, "一言难尽"),
    CANDID(13698817u, "难得糊涂"),
    EMO(7931137u, "emo中"),
    IT_IS_HARD(2491393u, "我太难了"),
    I_AM_OK(14485249u, "我想开了"),
    I_AM_FINE(1836033u, "我没事"),
    WANT_PEACE(2425857u, "想静静"),
    EASY_GOING(2294785u, "悠哉哉"),
    WEAK_SIGNAL(15926017u, "信号弱"),
    SLEEPING(16253697u, "睡觉中"),
    DOING_HOMEWORK(14419713u, "肝作业"),
    STUDYING(16384769u, "学习中"),
    WORKING_HARD(15140609u, "搬砖中"),
    SLACKING_OFF(1312001u, "摸鱼中"),
    BORED(2360321u, "无聊中"),
    TIMI(197633u, "timi中"),
    DREAM_TOGETHER(15271681u, "一起元梦"),
    SEEKING_PARTNER(15337217u, "求星搭子"),
    STAYING_UP_LATE(525313u, "熬夜中"),
    WATCHING_DRAMA(16581377u, "追剧中"),
    CUSTOM_STATUS(13633281u, "自定义状态");
}