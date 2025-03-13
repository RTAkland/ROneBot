/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/14
 */

package cn.rtast.rob.annotations

/**
 * 添加一个过滤器, 可以指定匹配模式
 * 默认为包括[TextMatchMode.CONTAINS]
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class TextFilter(val value: String, val mode: TextMatchMode = TextMatchMode.CONTAINS)

/**
 * 使用正则表达式来创建过滤器
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class TextRegexFilter(val value: String, val mode: TextRegexMatchMode = TextRegexMatchMode.FULL)

/**
 * 纯文本的匹配模式
 */
public enum class TextMatchMode {
    /**
     * 包括
     */
    CONTAINS,

    /**
     * 不包括
     */
    NOT_CONTAINS,

    /**
     * 等于
     */
    EQUALS,

    /**
     * 不等于
     */
    NOT_EQUALS
}

/**
 * 正则的匹配模式
 */
public enum class TextRegexMatchMode {
    /**
     * 完全匹配
     */
    FULL,

    /**
     * 部分匹配
     */
    PARTIAL,

    /**
     * 包括完整的单词
     */
    CONTAINS_WORD,
}