# 获取Bot管理器

Bot实例被存储在一个名为`OneBotFactory.botManager`的属性内, 你可以通过`OneBotFactory.botManager`来获取Bot实例管理器

```kotlin
suspend fun main() {
    val manager: BotManager = OneBotFactory.botManager
}
```

# 获取Bot实例

你可以根据Bot的QQ号来获取对应的实例

```kotlin
suspend fun main() {
    // 444444444是Bot的QQ号， 后面需要接上一个拓展属性`.ID`
    val bot: BotInstance = OneBotFactory.botManager[444444444.ID]
}
```

> 获取到Bot实例之后可以访问bot实例的`.action`对象来操作API

# 禁用/启用Bot实例

## 禁用

```kotlin
suspend fun main() {
    val instance1 = OneBotFactory.createClient(...)
    OneBotFactory.botManager.disableBotInstance(instance1)
}
```

> 需要注意的一点是, 如果禁用了Bot实例那么你的程序将不会接收到除`RawEvent`事件或者`onRawMessage`事件以外的任何事件通知,
> 你可以在这两个事件内启用Bot

需要禁用所有的Bot实例只将`disableBotInstance()`方法替换为`disableAllBots()`

## 启用

```kotlin
suspend fun main() {
    val instance1 = OneBotFactory.createClient(...)
    OneBotFactory.botManager.enableBotInstance(instance1)
}
```

启用所有的Bot实例只需要将`enableBotInstance()`替换为`enableAllBots()`

# 通过Action对象获取Bot实例

```kotlin
suspend fun main() {
    val instance1 = OneBotFactory.createClient(...)
    instance1.onEvent<GroupMessageEvent> {
        println(OneBotFactory.botManager.getBotInstanceByAction(it.action))
    }
}
```
