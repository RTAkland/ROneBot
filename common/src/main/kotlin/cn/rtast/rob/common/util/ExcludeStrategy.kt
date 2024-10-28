/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.common.util

import cn.rtast.rob.common.annotations.ExcludeField
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

/**
 * 创建自定义Exclude策略用于不处理被[ExcludeField]注解的字段
 */
class ExcludeStrategy : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(ExcludeField::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>): Boolean {
        return false
    }
}