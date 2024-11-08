/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/31
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

/**
 * 戳一戳的Enums
 */
enum class PokeMessage(val pokeName: String, val type: Int, val id: Int) {
    Poke("戳一戳", 1, -1), Like("比心", 2, -1), Heartbroken("心碎", 4, -1),
    SixSixSix("666", 5, -1), ShowLive("比心", 2, -1), FangDaZhao("放大招", 6, -1),
    BaoBeiQiu("宝贝球", 126, 2011), Rose("玫瑰花", 126, 2007), ZhaoHuanShu("召唤术", 126, 2006),
    RangNiPi("让你皮", 126, 2009), JeiYin("结印", 126, 2005), ShouLei("手雷", 126, 2004),
    GouYin("勾引", 126, 2003), ZhuaYiXia("抓一下", 126, 2001), SuiPing("碎屏", 126, 2002),
    QiaoMen("敲门", 126, 2002)
}