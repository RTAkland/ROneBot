/*
 * Copyright © 2025 RTAkland
 * Date: 2025/10/21 23:49
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.event

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.util.toJson
import kotlinx.serialization.Serializable

public typealias EventTopic = String

/**
 * 事件的信息
 * @param cls 事件的KClass类型, 无实际反射操作
 * @param topics 事件的`topics`主题
 * @param description 事件的具体描述
 */
@Serializable
public data class EventMeta(
    /**
     * 事件类
     */
    val cls: String,
    /**
     * 和事件相关的主题
     */
    val topics: Set<String>,
    /**
     * 事件的描述
     */
    val description: String,
)

@Serializable
@Suppress("CLASSNAME")
private data class _EventRegistry(
    /**
     * 所有的主题
     */
    val topics: Set<EventTopic>,
    /**
     * 所有的事件
     */
    val events: Set<EventMeta>,
)

/**
 * 事件注册表管理器
 */
public class EventRegistry @InternalROneBotApi constructor() {
    private val events: MutableMap<String, MutableSet<EventMeta>> = mutableMapOf()

    /**
     * 注册事件元数据
     */
    @InternalROneBotApi
    public fun register(meta: EventMeta) {
        for (topic in meta.topics) {
            events.getOrPut(topic) { mutableSetOf() }.add(meta)
        }
    }

    /**
     * 通过字符串主题来搜索
     * @param topic 主题
     * `EventTopic` = `String`
     * @see EventTopic
     */
    public fun search(topic: EventTopic): Set<EventMeta> = events[topic.lowercase()] ?: emptySet()

    /**
     * 多主题搜索
     * @param topics 主题
     * `EventTopic` = `String`
     * @see EventTopic
     */
    public fun search(vararg topics: EventTopic): Set<EventMeta> {
        if (topics.isEmpty()) return emptySet()
        val normalized = topics.map { it.lowercase() }
        var result = events[normalized.first()]?.toSet() ?: return emptySet()
        for (topic in normalized.drop(1)) {
            result = result.intersect((events[topic] ?: emptySet()).toSet())
        }
        return result
    }

    /**
     * 将已注册的事件格式化输出为JSON文本
     * @see _EventRegistry
     * `实验性`
     */
    @OptIn(InternalROneBotApi::class)
    @ExperimentalROneBotApi
    public fun formatToJson(): String =
        _EventRegistry(events.keys.sorted().toSet(), events.values.flatten().toSet()).toJson()

    /**
     * 创建一个空的伴生对象,
     * 所有的主题topics都以拓展属性的方式
     * 被`插入`到这个伴生对象中
     */
    public companion object Topics
}
