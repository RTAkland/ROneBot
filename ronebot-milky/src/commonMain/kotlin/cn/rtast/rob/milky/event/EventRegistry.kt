/*
 * Copyright © 2025 RTAkland
 * Date: 2025/10/21 23:49
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event

import cn.rtast.rob.milky.event.milky.BotInstanceCreatedEvent
import cn.rtast.rob.milky.event.milky.BotInstanceDisposedEvent
import cn.rtast.rob.milky.event.ws.packed.BotOfflineEvent
import cn.rtast.rob.milky.event.ws.packed.FriendFileUploadEvent
import cn.rtast.rob.milky.event.ws.packed.FriendNudgeEvent
import cn.rtast.rob.milky.event.ws.packed.FriendRequestEvent
import cn.rtast.rob.milky.event.ws.packed.GroupAdminChangeEvent
import cn.rtast.rob.milky.event.ws.packed.GroupEssenceMessageChangeEvent
import cn.rtast.rob.milky.event.ws.packed.GroupFileUploadEvent
import cn.rtast.rob.milky.event.ws.packed.GroupInvitationRequestEvent
import cn.rtast.rob.milky.event.ws.packed.GroupInvitedJoinRequestEvent
import cn.rtast.rob.milky.event.ws.packed.GroupJoinRequestEvent
import cn.rtast.rob.milky.event.ws.packed.GroupMemberDecreaseEvent
import cn.rtast.rob.milky.event.ws.packed.GroupMemberIncreaseEvent
import cn.rtast.rob.milky.event.ws.packed.GroupMessageEvent
import cn.rtast.rob.milky.event.ws.packed.GroupMessageReactionEvent
import cn.rtast.rob.milky.event.ws.packed.GroupMuteEvent
import cn.rtast.rob.milky.event.ws.packed.GroupNameChangeEvent
import cn.rtast.rob.milky.event.ws.packed.GroupNudgeEvent
import cn.rtast.rob.milky.event.ws.packed.GroupWholeMuteEvent
import cn.rtast.rob.milky.event.ws.packed.MessageRecallEvent
import cn.rtast.rob.milky.event.ws.packed.MessageReceiveEvent
import cn.rtast.rob.milky.event.ws.packed.PrivateMessageEvent
import cn.rtast.rob.milky.event.ws.packed.RawMessageEvent
import cn.rtast.rob.milky.event.ws.packed.WebsocketConnectedEvent
import cn.rtast.rob.milky.event.ws.packed.WebsocketDisconnectedEvent
import kotlin.reflect.KClass

public typealias EventTopic = String

/**
 * 事件的信息
 * @param type 事件的KClass类型
 * @param topics 事件的`topics`主题
 * @param description 事件的具体描述
 */
public data class EventMeta(
    val type: KClass<out MilkyEvent>,
    val topics: Set<String>,
    val description: String,
)
/**
 * 事件注册表管理器
 */
public class MilkyEventRegistry internal constructor() {
    private val metaList = mutableListOf<EventMeta>()
    private val topicToEvents = mutableMapOf<String, MutableSet<EventMeta>>()

    private fun register(meta: EventMeta) {
        metaList.add(meta)
        for (topic in meta.topics) {
            topicToEvents.getOrPut(topic) { mutableSetOf() }.add(meta)
        }
    }

    /**
     * 通过字符串主题来搜索
     * @param topic 主题
     * `EventTopic` = `String`
     */
    public fun search(topic: EventTopic): Set<EventMeta> = topicToEvents[topic.lowercase()] ?: emptySet()

    /**
     * 多主题搜索
     */
    public fun search(vararg topics: EventTopic): Set<EventMeta> {
        if (topics.isEmpty()) return emptySet()
        val normalized = topics.map { it.lowercase() }
        var result = topicToEvents[normalized.first()]?.toSet() ?: return emptySet()
        for (topic in normalized.drop(1)) {
            result = result.intersect((topicToEvents[topic] ?: emptySet()).toSet())
        }
        return result
    }

    public object Topics {
        /**
         * 框架内部事件
         */
        public const val FRAMEWORK: EventTopic = "framework"

        /**
         * Bot基础事件
         */
        public const val SYSTEM: EventTopic = "system"

        /**
         * 好友事件
         */
        public const val FRIEND: EventTopic = "friend"

        /**
         * 群聊事件
         */
        public const val GROUP: EventTopic = "system"

        /**
         * 消息事件
         */
        public const val MESSAGE: EventTopic = "system"

        /**
         * 文件事件
         */
        public const val FILE: EventTopic = "system"

        /**
         * 请求事件
         */
        public const val REQUEST: EventTopic = "system"

        /**
         * 通知事件
         */
        public const val NOTICE: EventTopic = "system"

        /**
         * 其他事件
         */
        public const val OTHER: EventTopic = "other"

        /**
         * 戳一戳
         */
        public const val NUDGE: EventTopic = "nudge"

        /**
         * 变更
         */
        public const val CHANGE: EventTopic = "change"
    }

    init {
        register(EventMeta(BotInstanceCreatedEvent::class, setOf(Topics.SYSTEM, "初始化"), "Bot初始化"))
        register(EventMeta(BotInstanceDisposedEvent::class, setOf(Topics.SYSTEM, "释放", "退出"), "Bot被释放"))
        register(EventMeta(BotOfflineEvent::class, setOf(Topics.SYSTEM, "离线"), "Bot离线"))
        register(EventMeta(FriendFileUploadEvent::class, setOf(Topics.FILE, Topics.FRIEND), "好友发送文件"))
        register(EventMeta(FriendNudgeEvent::class, setOf(Topics.FILE, Topics.NUDGE, Topics.FRIEND), "好友戳一戳"))
        register(EventMeta(FriendRequestEvent::class, setOf(Topics.REQUEST, Topics.FRIEND, "加好友"), "加好友请求"))
        register(
            EventMeta(
                GroupAdminChangeEvent::class,
                setOf(Topics.NOTICE, Topics.GROUP, Topics.CHANGE, Topics.CHANGE, "管理员"),
                "群管理员变更"
            )
        )
        register(
            EventMeta(
                GroupEssenceMessageChangeEvent::class,
                setOf(Topics.MESSAGE, Topics.GROUP, Topics.NOTICE, Topics.CHANGE, "精华消息", "群精华"),
                "群精华消息变更"
            )
        )
        register(EventMeta(GroupFileUploadEvent::class, setOf(Topics.FILE, Topics.GROUP), "群文件上传"))
        register(
            EventMeta(
                GroupInvitationRequestEvent::class,
                setOf(Topics.REQUEST, Topics.GROUP, "加群", "邀请"),
                "其他用户邀请自己(Bot)进群"
            )
        )
        register(
            EventMeta(
                GroupInvitedJoinRequestEvent::class,
                setOf(Topics.REQUEST, Topics.GROUP, "邀请"),
                "其他用户邀请其他用户进群"
            )
        )
        register(EventMeta(GroupJoinRequestEvent::class, setOf(Topics.REQUEST, Topics.GROUP, "加群"), "加群请求"))
        register(
            EventMeta(
                GroupMemberDecreaseEvent::class,
                setOf(Topics.NOTICE, Topics.GROUP, Topics.CHANGE, "成员"),
                "成员减少"
            )
        )
        register(
            EventMeta(
                GroupMemberIncreaseEvent::class,
                setOf(Topics.NOTICE, Topics.GROUP, Topics.CHANGE, "成员"),
                "成员增加"
            )
        )
        register(EventMeta(GroupMessageEvent::class, setOf(Topics.MESSAGE, Topics.GROUP), "群消息"))
        register(
            EventMeta(
                GroupMessageReactionEvent::class,
                setOf(Topics.MESSAGE, Topics.GROUP, Topics.NOTICE, Topics.CHANGE, "回应"),
                "群消息回应"
            )
        )
        register(EventMeta(GroupMuteEvent::class, setOf(Topics.NOTICE, Topics.GROUP, "禁言"), "用户被禁言"))
        register(
            EventMeta(
                GroupNameChangeEvent::class,
                setOf(Topics.NOTICE, Topics.GROUP, Topics.CHANGE, "改名"),
                "群名称变更"
            )
        )
        register(EventMeta(GroupNudgeEvent::class, setOf(Topics.NUDGE, Topics.GROUP), "群聊戳一戳"))
        register(EventMeta(GroupWholeMuteEvent::class, setOf(Topics.NOTICE, Topics.GROUP, "禁言"), "全员禁言"))
        register(
            EventMeta(
                MessageReceiveEvent::class,
                setOf(Topics.MESSAGE, Topics.FRIEND, Topics.GROUP),
                "接收到消息(无论是群聊、私聊和临时会话)"
            )
        )
        register(
            EventMeta(
                MessageRecallEvent::class,
                setOf(Topics.NOTICE, Topics.MESSAGE, Topics.FRIEND, Topics.GROUP, "撤回"),
                "消息被撤回(无论是群聊、私聊和临时会话)"
            )
        )
        register(
            EventMeta(
                PrivateMessageEvent::class,
                setOf(Topics.MESSAGE, Topics.FRIEND, "私聊"),
                "接收到私聊消息(临时会话)"
            )
        )
        register(
            EventMeta(
                RawMessageEvent::class,
                setOf(Topics.FRAMEWORK, Topics.OTHER, "原始消息"),
                "Milky实现下发的原始JSON消息"
            )
        )
        register(
            EventMeta(
                WebsocketConnectedEvent::class,
                setOf(Topics.FRAMEWORK, "已连接"),
                "Websocket已连接到Milky实现"
            )
        )
        register(
            EventMeta(
                WebsocketDisconnectedEvent::class,
                setOf(Topics.FRAMEWORK, "断开连接"),
                "Websocket从Milky实现断开连接"
            )
        )
    }
}
