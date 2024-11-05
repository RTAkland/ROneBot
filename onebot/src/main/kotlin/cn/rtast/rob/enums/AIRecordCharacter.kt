/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/3
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

/**
 * 定义几个常用的AI音色枚举类
 */
enum class AIRecordCharacter(val characterId: String, val chineseName: String) {
    XiaoXin("lucy-voice-laibixiaoxin", "小新"), MonkeyKing("lucy-voice-houge", "猴哥"),
    SiLang("lucy-voice-silang", "四郎"), DongBeiLaoMei("lucy-voice-guangdong-f1", "东北老妹儿"),
    GuangXiBiaoGe("lucy-voice-guangxi-m1", "广西大表哥"), DaJi("lucy-voice-daji", "妲己"),
    LiZeYan("lucy-voice-lizeyan", "霸道总裁"), SuXinJieJie("lucy-voice-suxinjiejie", "酥心御姐"),
    ShuoShuXianSheng("lucy-voice-m8", "说书先生"), HanHanXiaoDi("lucy-voice-male1", "憨憨小弟"),
    HanHouLaoGe("lucy-voice-male3", "憨厚老哥"), LvBu("lucy-voice-lvbu", "吕布"),
    YuanQiShaoNv("lucy-voice-xueling", "元气少女"), WenYiShaoNv("lucy-voice-f37", "文艺少女"),
    CiXingDaShu("lucy-voice-male2", "磁性大叔"), LinJiaXiaoMei("lucy-voice-female1", "邻家小妹"),
    DiChenNanSheng("lucy-voice-m14", "低沉男生"), AoJiaoShaoNv("lucy-voice-f38", "傲娇少女"),
    DieXiNanYou("lucy-voice-m101", "爹系男友"), NuanXinJieJie("lucy-voice-female2", "暖心姐姐"),
    WenRouMeiMei("lucy-voice-f36", "温柔妹妹"), ShuXiangShaoNv("lucy-voice-f34", "书香少女");

    companion object {
        fun forName(id: String) = entries.find { it.characterId == id }
        fun forChineseName(name: String) = entries.find { it.chineseName == name }
    }
}