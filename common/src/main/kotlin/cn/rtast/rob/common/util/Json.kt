/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


package cn.rtast.rob.common.util

import cn.rtast.rob.common.gson
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

fun Any.toJson(): String {
    return gson.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}

/**
 * 自定义注解标注了哪些字段可以不被序列化/反序列化
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ExcludeField

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
